package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import utils.MsgStack;
import utils.StateAndSemAction;
import utils.Token;
import utils.ElementoTS;
import utils.semantic.SemanticActionAddCharacter;
import utils.semantic.SemanticActionConstEnd;
import utils.semantic.SemanticActionFinID;
import utils.semantic.SemanticActionInvalidAndReturnC;
import utils.semantic.SemanticActionInvalidCharact;
import utils.semantic.SemanticActionLineCounter;
import utils.semantic.SemanticActionReturnAndUp;
import utils.semantic.SemanticActionReturnCharacter;

public class Lexicon {
	private final int END_OF_FILE = 0;
	private List<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStackLex;
	private StateAndSemAction[][] stateMachine;
	private Hashtable<String, ElementoTS> simbTable;
	private int actualCharac;
	private String actualLexeme;
	private int ready;

	public final static short ASSIGN=269;
	public final static short ID=259;
	public final static short CTE=260;
	public final static short LET=270;
	public final static short GET=271;
	public final static short EQ=272;
	public final static short DIF=273;
	public final static short CAD=274;
	
	public Lexicon(String srcCode, MsgStack msgStack, Hashtable<String, ElementoTS> simbTable) {
		nlCounter = 0;
		this.msgStackLex = msgStack;
		this.simbTable = simbTable;
		programBuffer = new ArrayList<Integer>();
		ready = openFile(srcCode);
		stateMachine = new StateAndSemAction[9][15];
		initStateMachine();
		initSimbTable();
	}
	
	private int range(int i) {
		return  (i >= 65 && i <= 90 || i>= 97 && i <= 122)? 0 : // Letras
				( i >= 48 && i <= 57)? 1 : 						// Digitos
				(i == 95)? 2 : 									// '_'
				(i == 36)? 3 :									// '$' (debería ser Fin de archivo)
				(i >= 40 && i <= 47 || i==91 || i==93)? 4 :		// Símbolos literales
				(i==123)? 5 :									// '{'
				(i==125)? 6 :									// '}'
				(i==60)? 7 :									// '<'
				(i==62)? 8 :									// '>'
				(i==61)? 9 :									// '='
				(i==10)? 10 :									// Salto de línea
				(i==35)? 11 :									// '#'
				(i==9 || i==32)? 12 :							// Espacio y TAB
				(i==58)? 14 :									// ':'
				13;												// Otros
		}
	
	public Token getNewToken() {
		actualLexeme = ""; //Limpia la variable
		int state = 0;
		int previous_state = -1;
		while(state != -1) //Mientras que no llegue a F, itera sobre la tabla de estados
		{
			actualCharac = programBuffer.get(0); //Lee el primer caracter
			programBuffer.remove(0); //Lo elimina del buffer
			int simbol = range(programBuffer.get(0)); //Ve a que columna tiene que ir
			if (stateMachine[state][simbol].getSemanticAction() != null)
				stateMachine[state][simbol].getSemanticAction().action(this); //Si tiene, realiza una A.S.
			previous_state = state;
			state = stateMachine[state][simbol].getState(); //Cambia de estado
		}
		switch (previous_state) {
		case 0:
			return new Token(actualCharac); //Literal directo
		case 1:
			return new Token(ID, actualLexeme);
		case 2:
			return new Token(CTE, actualLexeme);
		case 3:
			return new Token(CAD, actualLexeme);
		case 4:
			if (actualLexeme.length() == 1)
				return new Token(actualCharac);
			else
				return new Token(GET);
		case 5:
			if (actualLexeme.length() == 1)
				return new Token(actualCharac);
			else if (actualLexeme == "<=")
				return new Token(LET);
			else
				return new Token(DIF);
		case 6:
			if (actualLexeme.length() == 1)
				return new Token(actualCharac);
			else
				return new Token(EQ);
		case 8:
			if (actualLexeme.length() == 1)
				return new Token(actualCharac);
			else
				return new Token(ASSIGN);
		}
		return null; //No llega acá, es para que no de error de retorno
	}
	
	private int openFile (String src){
		File file = new File(src);
	    if (!file.exists()) {
	    	addMsg("ERROR: Archivo inexistente");
	      return 1;
	    }
	    if (!(file.isFile() && file.canRead())) {
	    	addMsg("ERROR: Archivo sin permiso de lectura");
	      return 1;
	    }
	    try {
	      FileInputStream fis = new FileInputStream(file);
	      while (fis.available() > 0)
	    	//Se agrega uno por uno los caracteres que contiene el file input
	        programBuffer.add(fis.read());
	    //Se agrega artificialmente el fin de archivo
	      programBuffer.add(END_OF_FILE); 
	      fis.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	      addMsg("ERROR: Error de lectura en archivo");
	      return 1;
	    }
	    return 0;
	}
	
	private void initSimbTable()
	{
		//Carga de todas las palabras reservadas (PR) en la tabla de símbolos
		this.simbTable.put("begin", new ElementoTS(257,"PR"));
		this.simbTable.put("end", new ElementoTS(258,"PR"));
		this.simbTable.put("int", new ElementoTS(261,"PR"));
		this.simbTable.put("long", new ElementoTS(262,"PR"));
		this.simbTable.put("if", new ElementoTS(263,"PR"));
		this.simbTable.put("else", new ElementoTS(264,"PR"));
		this.simbTable.put("end_if", new ElementoTS(265,"PR"));
		this.simbTable.put("foreach", new ElementoTS(266,"PR"));
		this.simbTable.put("in", new ElementoTS(267,"PR"));
		this.simbTable.put("print", new ElementoTS(268,"PR"));
	}
	
	private void initStateMachine () {
		//Carga de maquina de estados
		stateMachine[0][0] = new StateAndSemAction(1,new SemanticActionAddCharacter());
		stateMachine[0][1] = new StateAndSemAction(2,new SemanticActionAddCharacter());
		stateMachine[0][2] = new StateAndSemAction(0,new SemanticActionInvalidCharact());
		stateMachine[0][3] = new StateAndSemAction(-1,new SemanticActionAddCharacter());
		stateMachine[0][4] = new StateAndSemAction(-1,null);
		stateMachine[0][5] = new StateAndSemAction(3,null);
		stateMachine[0][6] = new StateAndSemAction(0,new SemanticActionInvalidCharact());
		stateMachine[0][7] = new StateAndSemAction(5,new SemanticActionAddCharacter());
		stateMachine[0][8] = new StateAndSemAction(4,new SemanticActionAddCharacter());
		stateMachine[0][9] = new StateAndSemAction(6,new SemanticActionAddCharacter());
		stateMachine[0][10] = new StateAndSemAction(0,new SemanticActionLineCounter());
		stateMachine[0][11] = new StateAndSemAction(7,null);
		stateMachine[0][12] = new StateAndSemAction(0,null);
		stateMachine[0][13] = new StateAndSemAction(0,new SemanticActionInvalidCharact());
		stateMachine[0][14] = new StateAndSemAction(8, new SemanticActionAddCharacter());
		stateMachine[1][0] = new StateAndSemAction(0,new SemanticActionInvalidCharact());
		stateMachine[1][1] = new StateAndSemAction(1,new SemanticActionAddCharacter());
		stateMachine[1][2] = new StateAndSemAction(1,new SemanticActionAddCharacter());
		stateMachine[1][3] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][4] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][5] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][6] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][7] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][8] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][9] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][10] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][11] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][12] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][13] = new StateAndSemAction(-1,new SemanticActionFinID() );
		stateMachine[1][14] = new StateAndSemAction(-1, new SemanticActionFinID());
		stateMachine[2][0] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][1] = new StateAndSemAction(2,new SemanticActionAddCharacter());
		stateMachine[2][2] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][3] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][4] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][5] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][6] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][7] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][8] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][9] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][10] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][11] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][12] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][13] = new StateAndSemAction(-1,new SemanticActionConstEnd());
		stateMachine[2][14] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[3][0] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][1] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][2] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][3] = new StateAndSemAction(-1,new SemanticActionAddCharacter());
		stateMachine[3][4] = new StateAndSemAction(3,new SemanticActionReturnAndUp());
		stateMachine[3][5] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][6] = new StateAndSemAction(-1,new SemanticActionAddCharacter());
		stateMachine[3][7] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][8] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][9] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][10] = new StateAndSemAction(3,new SemanticActionLineCounter());
		stateMachine[3][11] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][12] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][13] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[3][14] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[4][0] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][1] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][2] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][3] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][4] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][5] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][6] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][7] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][8] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][9] = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[4][10] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][11] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][12] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][13] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][14] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][0] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][1] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][2] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][3] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][4] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][5] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][6] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][7] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][8] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[5][9] = new StateAndSemAction(3,new SemanticActionAddCharacter());
		stateMachine[5][10] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][11] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][12] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][13] = new StateAndSemAction(3,new SemanticActionReturnCharacter());
		stateMachine[5][14] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][0] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][1] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][2] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][3] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][4] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][5] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][6] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][7] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][8] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][9] = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[6][10] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][11] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][12] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][13] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[6][14] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[7][0] = new StateAndSemAction(7, null);
		stateMachine[7][1] = new StateAndSemAction(7, null);
		stateMachine[7][2] = new StateAndSemAction(0, new SemanticActionReturnCharacter());
		stateMachine[7][3] = new StateAndSemAction(7, null);
		stateMachine[7][4] = new StateAndSemAction(7, null);
		stateMachine[7][5] = new StateAndSemAction(7, null);
		stateMachine[7][6] = new StateAndSemAction(7, null);
		stateMachine[7][7] = new StateAndSemAction(7, null);
		stateMachine[7][8] = new StateAndSemAction(7, null);
		stateMachine[7][9] = new StateAndSemAction(7, null);
		stateMachine[7][10] = new StateAndSemAction(0, new SemanticActionLineCounter());
		stateMachine[7][11] = new StateAndSemAction(7, null);
		stateMachine[7][12] = new StateAndSemAction(7, null);
		stateMachine[7][13] = new StateAndSemAction(7, null);
		stateMachine[7][14] = new StateAndSemAction(7,null);
		stateMachine[8][0] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][1] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][2] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][3] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][4] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][5] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][6] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][7] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][8] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][9] = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[8][10] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][11] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][12] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][13] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
		stateMachine[8][14] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturnC());
	}

	public boolean isReady(){ return (ready == 0); }
	
	public void addMsg(String msg){ this.msgStackLex.addMsg(msg); }
	
	public int getNLCounter() { return nlCounter; }
	
	public int getActualCharac() { return actualCharac; }
	
	public String getActualLexeme(){ return actualLexeme; }
	
	public void returnCharacter() { programBuffer.add(0, actualCharac); }
	
	public void addCharacter() { actualLexeme = actualLexeme + (char)actualCharac; }
	
	public void addNlCounter() { nlCounter = nlCounter+1; }
	
}
