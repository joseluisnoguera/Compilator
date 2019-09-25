package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import utils.MsgStack;
import utils.SemanticAction;
import utils.SemanticActionAddCharacter;
import utils.SemanticActionInvalidCharact;
import utils.SemanticActionLineCounter;
import utils.Token;

public class Lexicon {
	private final int END_OF_FILE = -1;
	private int filePtr;
	private List<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStack;
	//private Hashtable<Integer, Hashtable<Character, Integer>> stateMachine;
	private Pair[][] stateMachine;
	private Hashtable<Character, SemanticAction> semanticAction;
	private Hashtable<String, TuplaTS> simbTable;
	private Integer actualCharac;
	private String actualLexeme;

	
	public Lexicon(String srcCode, MsgStack msgStack, Hashtable<String, TuplaTS> simbTable) {
		this.nlCounter = 0;
		this.msgStack = msgStack;
		this.simbTable=simbTable;
		this.programBuffer=new ArrayList<>();
		openFile(srcCode);
		filePtr = 0;
		this.stateMachine=new Pair[8][13];
		initMachineState();
		initSemanticActions();
	}
	
	public void addMsg(String msg)
	{
		this.msgStack.addMsg(msg);
	}
	
	
	public int getNLCounter() { return nlCounter; }
	
	public Token getNewToken() {
		String lexeme; //El lexema y el conteo de nl debe ir en las AS (¿cómo ponerlo ahí?)
		//TODO: Itera en la máquina de estados hasta tener un token
		int state=0;
		while(!this.programBuffer.isEmpty())
		{
			//char charac=(char)this.programBuffer.get(0);
		}
			//TODO: Leer caracter, buscar en tabla de transiciones
		
			//TODO: Aplicar AS si existe
		
		return null;
	}
	
	private void openFile (String src){
		//TODO: Abrir y colocar puntero
		File file = new File(src);
	    if (!file.exists()) {
	      //TODO: Mensaje de error de archivo inexistente
	      return;
	    }
	    if (!(file.isFile() && file.canRead())) {
	      //TODO: Mensaje de error, archivo sin permiso de lectura
	      return;
	    }
	    try {
	      FileInputStream fis = new FileInputStream(file);
	      while (fis.available() > 0) {
	        programBuffer.add(fis.read());
	      }
	      programBuffer.add(END_OF_FILE); //Fin de archivo
	      fis.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	private void initMachineState() {
		//TODO: Inicializar con la matriz adecuada
		
		///Hecho por matriz[estado][simb]
		
		
		
		
	}
	
	private Integer getActualCharac()
	{
		return this.actualCharac;
	}
	
	public String getActualLexeme()
	{
		return this.actualLexeme;
	}
	
	private void initSemanticActions () {
		//TODO: Inicializar segun acciones
		this.stateMachine[0][0]= new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[0][1]=new Pair(2,new SemanticActionAddCharacter());
		this.stateMachine[0][2]=new Pair(0,new SemanticActionInvalidCharact());
		this.stateMachine[0][3]=new Pair(-1,new SemanticActionAddCharacter());
		this.stateMachine[0][4]=new Pair(-1,null);
		this.stateMachine[0][5]=new Pair(3,null);
		this.stateMachine[0][6]=new Pair(0,new SemanticActionInvalidCharact());
		this.stateMachine[0][7]=new Pair(5,new SemanticActionAddCharacter());
		this.stateMachine[0][8]=new Pair(4,new SemanticActionAddCharacter());
		this.stateMachine[0][9]=new Pair(6,new SemanticActionAddCharacter());
		this.stateMachine[0][10]=new Pair(0,new SemanticActionLineCounter());
		this.stateMachine[0][11]=new Pair(7,null);
		this.stateMachine[0][12]=new Pair(0,null);
		this.stateMachine[0][13]=new Pair(0,new SemanticActionInvalidCharact());
		this.stateMachine[1][0]=new Pair(0,new SemanticActionInvalidCharact());
		this.stateMachine[1][1]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][2]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][3]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][4]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][5]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][6]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][7]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][8]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[1][9]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[1][10]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[1][11]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[1][12]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[1][13]=new Pair(1,new SemanticActionAddChar());
		this.stateMachine[2][0]= ;
		this.stateMachine[2][1]= ;
		this.stateMachine[2][2]= ;
		this.stateMachine[2][3]= ;
		this.stateMachine[2][4]= ;
		this.stateMachine[2][5]= ;
		this.stateMachine[2][6]= ;
		this.stateMachine[2][7]= ;
		this.stateMachine[2][8]= ;
		this.stateMachine[2][9]= ;
		this.stateMachine[2][10]= ;
		this.stateMachine[2][11]= ;
		this.stateMachine[2][12]= ;
		this.stateMachine[3][0]= ;
		this.stateMachine[3][1]= ;
		this.stateMachine[3][2]= ;
		this.stateMachine[3][3]= ;
		this.stateMachine[3][4]= ;
		this.stateMachine[3][5]= ;
		this.stateMachine[3][6]= ;
		this.stateMachine[3][7]= ;
		this.stateMachine[3][8]= ;
		this.stateMachine[3][9]= ;
		this.stateMachine[3][10]= ;
		this.stateMachine[3][11]= ;
		this.stateMachine[3][12]= ;
		this.stateMachine[4][0]= ;
		this.stateMachine[4][1]= ;
		this.stateMachine[4][2]= ;
		this.stateMachine[4][3]= ;
		this.stateMachine[4][4]= ;
		this.stateMachine[4][5]= ;
		this.stateMachine[4][6]= ;
		this.stateMachine[4][7]= ;
		this.stateMachine[4][8]= ;
		this.stateMachine[4][9]= ;
		this.stateMachine[4][10]= ;
		this.stateMachine[4][11]= ;
		this.stateMachine[4][12]= ;
		this.stateMachine[5][0]= ;
		this.stateMachine[5][1]= ;
		this.stateMachine[5][2]= ;
		this.stateMachine[5][3]= ;
		this.stateMachine[5][4]= ;
		this.stateMachine[5][5]= ;
		this.stateMachine[5][6]= ;
		this.stateMachine[5][7]= ;
		this.stateMachine[5][8]= ;
		this.stateMachine[5][9]= ;
		this.stateMachine[5][10]= ;
		this.stateMachine[5][11]= ;
		this.stateMachine[5][12]= ;
		this.stateMachine[6][0]= ;
		this.stateMachine[6][1]= ;
		this.stateMachine[6][2]= ;
		this.stateMachine[6][3]= ;
		this.stateMachine[6][4]= ;
		this.stateMachine[6][5]= ;
		this.stateMachine[6][6]= ;
		this.stateMachine[6][7]= ;
		this.stateMachine[6][8]= ;
		this.stateMachine[6][9]= ;
		this.stateMachine[6][10]= ;
		this.stateMachine[6][11]= ;
		this.stateMachine[6][12]= ;
		this.stateMachine[7][0]= ;
		this.stateMachine[7][1]= ;
		this.stateMachine[7][2]= ;
		this.stateMachine[7][3]= ;
		this.stateMachine[7][4]= ;
		this.stateMachine[7][5]= ;
		this.stateMachine[7][6]= ;
		this.stateMachine[7][7]= ;
		this.stateMachine[7][8]= ;
		this.stateMachine[7][9]= ;
		this.stateMachine[7][10]= ;
		this.stateMachine[7][11]= ;
		this.StateMachine[7][12]= ;


	}

	public void returnCharacter() {
		// TODO Auto-generated method stub
		this.programBuffer.add(0, this.actualCharac);
		
	}
	
	public void addCharacter() {
		// TODO Auto-generated method stub
		this.actualLexeme=this.actualLexeme + this.actualCharac;
		
	}
	
	public void addNlCounter() {
		this.nlCounter=this.nlCounter+1;
	}
	
}
