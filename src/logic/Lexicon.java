package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

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
	private int actualCharac;
	private String actualLexeme;
	private int ready;
	private Queue queToken;

	
	public Lexicon(String srcCode, MsgStack msgStack, Hashtable<String, TuplaTS> simbTable) {
		this.nlCounter = 0;
		this.msgStackLex = msgStack;
		this.simbTable=simbTable;
		this.programBuffer=new ArrayList<Integer>();
		ready = openFile(srcCode);
		this.filePtr = 0;
		this.stateMachine=new Pair[9][15];
		this.queToken=null;
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
	
	private int range(int i) {
		if( i >=65 && i <= 90 || i >= 97 && i <= 122)
			return 0;//es letra mayus o minus
		else
			if( i >= 48 && i <= 57)
				return 1;//es digito
			else
				if (i == 95)
					return 2;//_
				else
					if(i == 36)
						return 3;//$
					else
						if(i >= 40 && i <= 47 || i==91 || i==93)
							return 4;//S.E
						else
							if(i==123)
								return 5;
							else
								if(i==125)
									return 6;
								else
									if(i==60)
										return 7;
									else
										if(i==62)
											return 8;
										else
											if(i==61)
												return 9;
											else
												if(i==10)
													return 10;//salto de linea
												else
													if(i==35)
														return 11;//#
													else
														if(i==9 || i==32)
															return 12;//TAB o space
														else
															if(i==58)
																return 14;//:
															else
																return 13;
		}
	
	public Token getNewToken() {
		actualLexeme = "";
		
		String lexeme= new String();
		int state=0;
		while(state != -1)
		{
			this.actualCharac = this.programBuffer.get(0);
			switch(range(this.programBuffer.get(0))) {
				case 0:
						this.stateMachine[state][0].getSemanticAction().action(this);
						state=this.stateMachine[state][0].getState();
				case 1:
						this.stateMachine[state][1].getSemanticAction().action(this);
						state=this.stateMachine[state][1].getState();
				case 2:
						this.stateMachine[state][2].getSemanticAction().action(this);
						state=this.stateMachine[state][2].getState();
				case 3:
						this.stateMachine[state][3].getSemanticAction().action(this);
						state=this.stateMachine[state][3].getState();
				case 4:
						this.stateMachine[state][4].getSemanticAction().action(this);
						state=this.stateMachine[state][4].getState();
				case 5:
						this.stateMachine[state][5].getSemanticAction().action(this);
						state=this.stateMachine[state][5].getState();
				case 6:
						this.stateMachine[state][6].getSemanticAction().action(this);
						state=this.stateMachine[state][6].getState();
				case 7:
						this.stateMachine[state][7].getSemanticAction().action(this);
						state=this.stateMachine[state][7].getState();
				case 8:
						this.stateMachine[state][8].getSemanticAction().action(this);
						state=this.stateMachine[state][8].getState();
				case 9:
						this.stateMachine[state][9].getSemanticAction().action(this);
						state=this.stateMachine[state][9].getState();
				case 10:
						this.stateMachine[state][10].getSemanticAction().action(this);
						state=this.stateMachine[state][10].getState();
				case 11:
						this.stateMachine[state][11].getSemanticAction().action(this);
						state=this.stateMachine[state][11].getState();
				case 12:
						this.stateMachine[state][12].getSemanticAction().action(this);
						state=this.stateMachine[state][12].getState();
				case 13:
						this.stateMachine[state][13].getSemanticAction().action(this);
						state=this.stateMachine[state][13].getState();
				case 14:
						this.stateMachine[state][14].getSemanticAction().action(this);
						state=this.stateMachine[state][14].getState();
															
			
			}
		}
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
	        programBuffer.add(fis.read());//Se agrega uno por uno los caracteres que contiene el file input
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
		return (int) this.actualCharac;
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
		this.actualLexeme=this.actualLexeme + (char)this.actualCharac;
		
	}
	
	public void addNlCounter() {
		this.nlCounter=this.nlCounter+1;
	}
	
	private void inicSimbTable()
	{
		this.simbTable.put("begin", new TuplaTS(257,"BEGIN"));
		this.simbTable.put("end", new TuplaTS(258,"END"));
		this.simbTable.put("int", new TuplaTS(261,"INT"));
		this.simbTable.put("long", new TuplaTS(262,"LONG"));
		this.simbTable.put("if", new TuplaTS(263,"IF"));
		this.simbTable.put("else", new TuplaTS(264,"ELSE"));
		this.simbTable.put("end_if", new TuplaTS(265,"END_IF"));
		this.simbTable.put("foreach", new TuplaTS(266,"FOREACH"));
		this.simbTable.put("in", new TuplaTS(267,"IN"));
		this.simbTable.put("print", new TuplaTS(268,"PRINT"));
	}
	
	
	
}
