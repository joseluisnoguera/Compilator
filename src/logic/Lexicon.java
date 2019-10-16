package logic;

import java.util.Hashtable;
import java.util.List;

import utils.MsgStack;
import utils.StateAndSemAction;
import utils.Token;
import utils.ElementoTS;
import utils.semantic.SemanticActionAddCharacter;
import utils.semantic.SemanticActionCADend;
import utils.semantic.SemanticActionConstEnd;
import utils.semantic.SemanticActionFinID;
import utils.semantic.SemanticActionInvalidAndReturn;
import utils.semantic.SemanticActionInvalidChar;
import utils.semantic.SemanticActionCountLine;
import utils.semantic.SemanticActionReturnAndAdd;
import utils.semantic.SemanticActionReturnCharacter;

public class Lexicon {
	private final int END_OF_FILE = 0; //Requerido por BYACC/J como fin de archivo
	private final int END_OF_TEXT = 3; //ASCII
	
	//Constantes declaradas por BYACC/J
	public final static short ASSIGN=269;
	public final static short ID=259;
	public final static short CTE=260;
	public final static short LET=270;
	public final static short GET=271;
	public final static short EQ=272;
	public final static short DIF=273;
	public final static short CAD=274;	
	
	private List<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStack;
	private MsgStack tokenStack;
	private StateAndSemAction[][] stateMachine;
	private Hashtable<String, ElementoTS> symbolTable;
	private int currentChar;
	private String currentLexeme;
	private int ready;
	
	public Lexicon(List<Integer> programBuffer, MsgStack msgStack, MsgStack tokenStack, Hashtable<String, ElementoTS> simbTable) {
		nlCounter = 1;
		this.msgStack = msgStack;
		this.tokenStack = tokenStack;
		this.symbolTable = simbTable;
		this.programBuffer = programBuffer;
		stateMachine = new StateAndSemAction[9][15];
		initStateMachine();
		initSimbTable();
	}
	
	private int range(int i) {
		return  (i >= 65 && i <= 90 || i>= 97 && i <= 122)? 0 : 			// Letras
				( i >= 48 && i <= 57)? 1 : 									// Digitos
				(i == 95)? 2 : 												// '_'
				(i == 3)? 3 :												// 'ETX' Reemplaza al fin de archivo, no viene en un texto normal, se agrega artificialmente al final del buffer de programa
				((i>=40 && i<=45) || i==47 || i==91 || i==93 || i==59)? 4 :	// Símbolos literales aceptados
				(i==123)? 5 :												// '{'
				(i==125)? 6 :												// '}'
				(i==60)? 7 :												// '<'
				(i==62)? 8 :												// '>'
				(i==61)? 9 :												// '='
				(i==10)? 10 :												// Salto de línea
				(i==35)? 11 :												// '#'
				(i==9 || i==32)? 12 :										// Espacio y TAB
				(i==58)? 14 :												// ':'
				13;															// Otros (está dando error de carácter inválido)
		}
	
	public Token getNewToken() {
		currentLexeme = ""; //Limpia la variable
		int state = 0;
		int previous_state = -1;
<<<<<<< HEAD
		while(state != -1) { //Mientras que no llegue a F, itera sobre la tabla de estados
<<<<<<< HEAD
=======
		while(state != -1) //Mientras que no llegue a F, itera sobre la tabla de estados
		{
<<<<<<< HEAD
<<<<<<< Updated upstream
>>>>>>> 6b7f13f... StateMachine
=======
>>>>>>> cddb5e5... Lex
			actualCharac = programBuffer.get(0); //Lee el primer caracter
=======
			currentChar = programBuffer.get(0); //Lee el primer caracter
>>>>>>> 1ade0f6... Funcion de carga de datos en paneles de la ventana
			programBuffer.remove(0); //Lo elimina del buffer
			int symbol = range(currentChar); //Ve a que columna tiene que ir con el caracter que obtuvo anteriormente
			if (stateMachine[state][symbol].getSemanticAction() != null)
				stateMachine[state][symbol].getSemanticAction().action(this); //Si tiene, realiza una A.S.
			previous_state = state; //Guarda el estado anterior para el control posterior
			state = stateMachine[state][symbol].getState(); //Cambia de estado
		}
<<<<<<< HEAD
<<<<<<< HEAD
		switch (previous_state) {
<<<<<<< HEAD
			case 0:
				if (currentChar == END_OF_TEXT) { //Es end of file y tiene que mandar 0, sino un token normal
					tokenStack.addMsg("Fin de archivo");
					return new Token(END_OF_FILE);
				}
				else {
					
					tokenStack.addMsg(String.valueOf((char)currentChar));
					return new Token(currentChar); //Literal directo
				}
			case 1:
				//Ver si el lexema es un PR, sino es un ID
				if (symbolTable.get(currentLexeme).getTipoToken().equals("PR")) {
					tokenStack.addMsg("Palabra reservada " + currentLexeme);
					return new Token(symbolTable.get(currentLexeme).getValue());
				}
				else {
					tokenStack.addMsg("Identificador " + currentLexeme);
					return new Token(ID, currentLexeme);
				}
			case 2:
				tokenStack.addMsg("Constante " + currentLexeme);
				return new Token(CTE, currentLexeme);
			case 3:
				tokenStack.addMsg("Cadena " + currentLexeme);
				return new Token(CAD, currentLexeme);
			case 4:
				if (currentLexeme.length() == 1) { // Solo >
 					tokenStack.addMsg(currentLexeme);
					return new Token((int)(currentLexeme.charAt(0)));
				}
				else {
					tokenStack.addMsg(">=");
					return new Token(GET);
				}
			case 5: 
				if (currentLexeme.length() == 1) { // Solo <
					tokenStack.addMsg(currentLexeme);
					return new Token((int)(currentLexeme.charAt(0)));
				}
				else if (currentLexeme.equals("<=")) {
					tokenStack.addMsg("<=");
					return new Token(LET);
				}
				else {
					tokenStack.addMsg("<>");
					return new Token(DIF);
				}
			case 6:
					tokenStack.addMsg("=="); //Si es = solo, lo completa como comparación
					return new Token(EQ);
			case 8:
					tokenStack.addMsg("Asignación"); //Si es :, lo completa como asignación
					return new Token(ASSIGN);
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
		switch (previous_state) {
>>>>>>> 334ecd7... SA
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
<<<<<<< HEAD
=======
			this.actualCharac = programBuffer.get(0); //Lee el primer caracter
			this.programBuffer.remove(0); //Lo elimina del buffer
			int simbol = range(this.programBuffer.get(0)); //Ve a que columna tiene que ir
			if (this.stateMachine[state][simbol].getSemanticAction() != null)
				this.stateMachine[state][simbol].getSemanticAction().action(this); //Si tiene, realiza una A.S.
			state = this.stateMachine[state][simbol].getState(); //Cambia de estado
		}
=======

>>>>>>> cddb5e5... Lex
		//< > = : son los unicos literales que agregan en lexeme
		if (actualLexeme.length() == 0 || actualLexeme == "<" || actualLexeme == ">" 
				|| actualLexeme == "="  || actualLexeme == ":")
			return new Token(actualCharac); //Entonces era un literal que pasa directo
		else 
			if(simbTable.containsKey(actualLexeme)) { //No era un literal, es PR, combinado de literales, ID, CTE o CAD
				this.simbTable.get(this.actualLexeme).setCantidad(1);
				if ((simbTable.get(actualLexeme).getTipoToken()) == "PR")
					{
					return new Token(simbTable.get(actualLexeme).getValue());
					}
				else 
					if ((simbTable.get(actualLexeme).getTipoToken()) == "ID") //Es un ID, una CTE o una CAD
						{
						return new Token(ID, actualLexeme); 
						}
					else 
						if ((simbTable.get(actualLexeme).getTipoToken()) == "CTE")
							{
							return new Token(CTE, actualLexeme);
							}
						else 
							{
							return new Token(CAD, actualLexeme); //Si no es ninguno de los anterior es CAD
							}
			} 
			else 
				
				return new Token(getCombLiteral(actualLexeme), this.actualLexeme); //Si no está en la TS es combinado de literales
	
<<<<<<< HEAD
	private int getCombLiteral(String combined) {
		int ret = 0;
		switch(combined) {
			case "<=": ret = LET;
			case ">=": ret = GET;
			case "==": ret = EQ;
			case "<>": ret = DIF;
>>>>>>> Stashed changes
>>>>>>> 6b7f13f... StateMachine
		}
=======
	

>>>>>>> cddb5e5... Lex
		return null; //No llega acá, es para que no de error de retorno
<<<<<<< HEAD
=======
>>>>>>> 334ecd7... SA
	}
		return null;
}
	
//	private int openFile (String src){
//		File file = new File(src);
//	    if (!file.exists()) {
//	    	addMsg("ERROR: Archivo inexistente");
//	      return 1;
//	    }
//	    if (!(file.isFile() && file.canRead())) {
//	    	addMsg("ERROR: Archivo sin permiso de lectura");
//	      return 1;
//	    }
//	    try {
//	      FileInputStream fis = new FileInputStream(file);
//	      while (fis.available() > 0)
//	    	//Se agrega uno por uno los caracteres que contiene el file input
//	        programBuffer.add(fis.read());
//	    //Se agrega artificialmente el fin de archivo
//	      programBuffer.add(END_OF_FILE); 
//	      fis.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	      addMsg("ERROR: Error de lectura en archivo");
//	      return 1;
//	    }
//	    return 0;
//	}
=======
=======
				}
=======
>>>>>>> 569d5c9... varios
		}
		return null; //No llega acá, es para que no de error por falta de retorno
>>>>>>> 0999ae6... varios
	}
>>>>>>> 1ade0f6... Funcion de carga de datos en paneles de la ventana
	
	private void initSimbTable()
	{
		//Carga de todas las palabras reservadas (PR) en la tabla de símbolos
		symbolTable.put("begin", new ElementoTS(257,"PR"));
		symbolTable.put("end", new ElementoTS(258,"PR"));
		symbolTable.put("int", new ElementoTS(261,"PR"));
		symbolTable.put("long", new ElementoTS(262,"PR"));
		symbolTable.put("if", new ElementoTS(263,"PR"));
		symbolTable.put("else", new ElementoTS(264,"PR"));
		symbolTable.put("end_if", new ElementoTS(265,"PR"));
		symbolTable.put("foreach", new ElementoTS(266,"PR"));
		symbolTable.put("in", new ElementoTS(267,"PR"));
		symbolTable.put("print", new ElementoTS(268,"PR"));
	}
	
	public void putSymbolTable(String lexeme, ElementoTS tupla) {
		symbolTable.put(lexeme, tupla);
	}
	
	public boolean containsKey(String lexeme)
	{
		return this.symbolTable.containsKey(lexeme);
	}
	
	//
	public Hashtable<String, ElementoTS> getSymbolTable() {
		return symbolTable;
	}
	
	public void increaseCounterSymbolTable() {
		symbolTable.get(currentLexeme).increaseCounter();
	}
	
	private void initStateMachine () {
		//Carga de maquina de estados [Estado,Caracteres] [Fila,Columna] de la matriz de transición
		
		stateMachine[0][0]  = new StateAndSemAction(1, new SemanticActionAddCharacter());
		stateMachine[0][1]  = new StateAndSemAction(2, new SemanticActionAddCharacter());
		stateMachine[0][2]  = new StateAndSemAction(-1, null);
		stateMachine[0][3]  = new StateAndSemAction(-1, null);
		stateMachine[0][4]  = new StateAndSemAction(-1, null);
		stateMachine[0][5]  = new StateAndSemAction(3, null);
		stateMachine[0][6]  = new StateAndSemAction(0, new SemanticActionInvalidChar());
		stateMachine[0][7]  = new StateAndSemAction(5, new SemanticActionAddCharacter());
		stateMachine[0][8]  = new StateAndSemAction(4, new SemanticActionAddCharacter());
		stateMachine[0][9]  = new StateAndSemAction(6, new SemanticActionAddCharacter());
		stateMachine[0][10] = new StateAndSemAction(0, new SemanticActionCountLine());
		stateMachine[0][11] = new StateAndSemAction(7, null);
		stateMachine[0][12] = new StateAndSemAction(0, null);
		stateMachine[0][13] = new StateAndSemAction(0, new SemanticActionInvalidChar());
		stateMachine[0][14] = new StateAndSemAction(8, new SemanticActionAddCharacter());
		
		//Nuevo estado//
		stateMachine[1][0]  = new StateAndSemAction(1, new SemanticActionAddCharacter());
		stateMachine[1][1]  = new StateAndSemAction(1, new SemanticActionAddCharacter());
		stateMachine[1][2]  = new StateAndSemAction(1, new SemanticActionAddCharacter());
		stateMachine[1][3]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][4]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][5]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][6]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][7]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][8]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][9]  = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][10] = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][11] = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][12] = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][13] = new StateAndSemAction(-1, new SemanticActionFinID() );
		stateMachine[1][14] = new StateAndSemAction(-1, new SemanticActionFinID());
		
		//Nuevo estado//
		stateMachine[2][0]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][1]  = new StateAndSemAction(2, new SemanticActionAddCharacter());
		stateMachine[2][2]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][3]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][4]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][5]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][6]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][7]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][8]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][9]  = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][10] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][11] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][12] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][13] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		stateMachine[2][14] = new StateAndSemAction(-1, new SemanticActionConstEnd());
		
		//Nuevo estado//
		stateMachine[3][0]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][1]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][2]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][3]  = new StateAndSemAction(-1, new SemanticActionReturnAndAdd());
		stateMachine[3][4]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][5]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][6]  = new StateAndSemAction(-1, new SemanticActionCADend());
		stateMachine[3][7]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][8]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][9]  = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][10] = new StateAndSemAction(3, new SemanticActionCountLine());
		stateMachine[3][11] = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][12] = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][13] = new StateAndSemAction(3, new SemanticActionAddCharacter());
		stateMachine[3][14] = new StateAndSemAction(3, new SemanticActionAddCharacter());
		
		//Nuevo estado//
		stateMachine[4][0]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][1]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][2]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][3]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][4]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][5]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][6]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][7]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][8]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][9]  = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[4][10] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][11] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][12] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][13] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[4][14] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		
		//Nuevo estado//
		stateMachine[5][0]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][1]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][2]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][3]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][4]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][5]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][6]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][7]  = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][8]  = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[5][9]  = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[5][10] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][11] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][12] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][13] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		stateMachine[5][14] = new StateAndSemAction(-1, new SemanticActionReturnCharacter());
		
		//Nuevo estado//
		stateMachine[6][0]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][1]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][2]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][3]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][4]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][5]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][6]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][7]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][8]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][9]  = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[6][10] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][11] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][12] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][13] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[6][14] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		
		//Nuevo estado//
		stateMachine[7][0]  = new StateAndSemAction(7, null);
		stateMachine[7][1]  = new StateAndSemAction(7, null);
		stateMachine[7][2]  = new StateAndSemAction(7, null);
		stateMachine[7][3]  = new StateAndSemAction(0, new SemanticActionReturnCharacter());
		stateMachine[7][4]  = new StateAndSemAction(7, null);
		stateMachine[7][5]  = new StateAndSemAction(7, null);
		stateMachine[7][6]  = new StateAndSemAction(7, null);
		stateMachine[7][7]  = new StateAndSemAction(7, null);
		stateMachine[7][8]  = new StateAndSemAction(7, null);
		stateMachine[7][9]  = new StateAndSemAction(7, null);
		stateMachine[7][10] = new StateAndSemAction(0, new SemanticActionCountLine());
		stateMachine[7][11] = new StateAndSemAction(7, null);
		stateMachine[7][12] = new StateAndSemAction(7, null);
		stateMachine[7][13] = new StateAndSemAction(7, null);
		stateMachine[7][14] = new StateAndSemAction(7, null);
		
		//Nuevo estado//
		stateMachine[8][0]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][1]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][2]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][3]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][4]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][5]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][6]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][7]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][8]  = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][9]  = new StateAndSemAction(-1, new SemanticActionAddCharacter());
		stateMachine[8][10] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][11] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][12] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][13] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
		stateMachine[8][14] = new StateAndSemAction(-1, new SemanticActionInvalidAndReturn());
	}

	public boolean isReady(){ return (ready == 0); }
	
	public void addMsg(String msg){ msgStack.addMsg(msg); }
	
	public int getNewLineCounter() { return nlCounter; }
	
	public int getCurrentCharacter() { return currentChar; }
	
	public String getCurrentLexeme(){ return currentLexeme; }
	
	public void updateLexeme(String lexeme) { currentLexeme = lexeme; }
	
	public void returnCharacter() { programBuffer.add(0, currentChar); }
	
	public void addCharacter() { currentLexeme = currentLexeme + (char)currentChar; }
	
	public void addNlCounter() { nlCounter = nlCounter+1; }
}