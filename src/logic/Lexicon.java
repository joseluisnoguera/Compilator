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
import utils.SemanticActionConstEnd;
import utils.SemanticActionFinID;
import utils.SemanticActionInvalidAndReturnC;
import utils.SemanticActionInvalidCharact;
import utils.SemanticActionLineCounter;
import utils.SemanticActionReturnAndUp;
import utils.SemanticActionReturnCharacter;
import utils.Token;

public class Lexicon {
	private final int END_OF_FILE = 0;
	private int filePtr;
	private List<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStackLex;
	private Pair[][] stateMachine;
	private Hashtable<Character, SemanticAction> semanticAction;
	private Hashtable<String, TuplaTS> simbTable;
	private Integer actualCharac;
	private String actualLexeme;
	private int ready;

	
	public Lexicon(String srcCode, MsgStack msgStack, Hashtable<String, TuplaTS> simbTable) {
		this.nlCounter = 0;
		this.msgStackLex = msgStack;
		this.simbTable=simbTable;
		this.programBuffer=new ArrayList<>();
		ready = openFile(srcCode);
		this.filePtr = 0;
		this.stateMachine=new Pair[9][15];
		initStateMachine();
	}
	
	public boolean isReady() {
		return (ready == 0);
	}
	
	public void addMsg(String msg)
	{
		this.msgStackLex.addMsg(msg);
	}
	
	
	public int getNLCounter() { return nlCounter; }
	
	public Token getNewToken() {
		actualLexeme = "";
		//TODO: Itera en la máquina de estados hasta tener un token
		int state=0;
		while(state != -1)
		{
			//char charac=(char)this.programBuffer.get(0);
			//TODO: Leer caracter, buscar en tabla de transiciones
			//TODO: Aplicar AS si existe
			//TODO: Update file pointer in each state change
		}
			
		
		return null;
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
	      while (fis.available() > 0) {
	        programBuffer.add(fis.read());
	      }
	      programBuffer.add(END_OF_FILE); //Fin de archivo
	      fis.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	      addMsg("ERROR: Error de lectura en archivo");
	      return 1;
	    }
	    return 0;
	}
	
	private Integer getActualCharac()
	{
		return this.actualCharac;
	}
	
	public String getActualLexeme()
	{
		return this.actualLexeme;
	}
	
	private void initStateMachine () {
		//TODO: Complete this method
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
		this.stateMachine[0][14]=new Pair(8, new SemanticActionAddCharacter());
		this.stateMachine[1][0]=new Pair(0,new SemanticActionInvalidCharact());
		this.stateMachine[1][1]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][2]=new Pair(1,new SemanticActionAddCharacter());
		this.stateMachine[1][3]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][4]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][5]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][6]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][7]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][8]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][9]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][10]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][11]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][12]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][13]=new Pair(-1,new SemanticActionFinID() );
		this.stateMachine[1][14]=new Pair(-1, new SemanticActionFinID());
		this.stateMachine[2][0]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][1]= new Pair(2,new SemanticActionAddCharacter());
		this.stateMachine[2][2]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][3]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][4]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][5]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][6]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][7]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][8]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][9]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][10]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][11]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][12]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][13]= new Pair(-1,new SemanticActionConstEnd());
		this.stateMachine[2][14]=new Pair(-1, new SemanticActionConstEnd());
		this.stateMachine[3][0]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][1]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][2]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][3]= new Pair(-1,new SemanticActionAddCharacter());
		this.stateMachine[3][4]= new Pair(3,new SemanticActionReturnAndUp());
		this.stateMachine[3][5]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][6]= new Pair(-1,new SemanticActionAddCharacter());
		this.stateMachine[3][7]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][8]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][9]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][10]= new Pair(3,new SemanticActionLineCounter());
		this.stateMachine[3][11]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][12]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][13]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[3][14]=new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[4][0]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][1]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][2]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][3]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][4]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][5]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][6]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][7]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][8]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][9]= new Pair(-1, new SemanticActionAddCharacter());
		this.stateMachine[4][10]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][11]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][12]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][13]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[4][14]=new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[5][0]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][1]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][2]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][3]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][4]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][5]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][6]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][7]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][8]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[5][9]= new Pair(3,new SemanticActionAddCharacter());
		this.stateMachine[5][10]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][11]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][12]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][13]= new Pair(3,new SemanticActionReturnCharacter());
		this.stateMachine[5][14]=new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][0]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][1]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][2]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][3]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][4]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][5]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][6]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][7]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][8]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][9]= new Pair(-1, new SemanticActionAddCharacter());
		this.stateMachine[6][10]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][11]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][12]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][13]= new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[6][14]=new Pair(-1, new SemanticActionReturnCharacter());
		this.stateMachine[7][0]= new Pair(7, null);
		this.stateMachine[7][1]= new Pair(7, null);
		this.stateMachine[7][2]= new Pair(0, new SemanticActionReturnCharacter());
		this.stateMachine[7][3]= new Pair(7, null);
		this.stateMachine[7][4]= new Pair(7, null);
		this.stateMachine[7][5]= new Pair(7, null);
		this.stateMachine[7][6]= new Pair(7, null);
		this.stateMachine[7][7]= new Pair(7, null);
		this.stateMachine[7][8]= new Pair(7, null);
		this.stateMachine[7][9]= new Pair(7, null);
		this.stateMachine[7][10]= new Pair(0, new SemanticActionLineCounter());
		this.stateMachine[7][11]= new Pair(7, null);
		this.stateMachine[7][12]= new Pair(7, null);
		this.stateMachine[7][13]= new Pair(7, null);
		this.stateMachine[7][14]=new Pair(7,null);
		this.stateMachine[8][0]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][1]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][2]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][3]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][4]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][5]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][6]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][7]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][8]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][9]= new Pair(-1, new SemanticActionAddCharacter());
		this.stateMachine[8][10]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][11]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][12]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][13]= new Pair(-1, new SemanticActionInvalidAndReturnC());
		this.stateMachine[8][14]=new Pair(-1, new SemanticActionInvalidAndReturnC());
	}

	public void returnCharacter() {
		this.programBuffer.add(0, this.actualCharac);
		
	}
	
	public void addCharacter() {
		this.actualLexeme=this.actualLexeme + this.actualCharac;
		
	}
	
	public void addNlCounter() {
		this.nlCounter=this.nlCounter+1;
	}
	
}
