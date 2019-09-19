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
import utils.Token;

public class Lexicon {
	private final int END_OF_FILE = -1;
	private int filePtr;
	private List<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStack;
	//private Hashtable<Integer, Hashtable<Character, Integer>> stateMachine;
	private Integer[][] stateMachine;
	private Hashtable<Character, SemanticAction> semanticAction;
	private Hashtable<Integer, String> simbTable;
	private Integer actualCharac;
	//cualquier cosa
	
	
	public Lexicon(String srcCode, MsgStack msgStack) {
		this.nlCounter = 0;
		this.msgStack = msgStack;
		this.programBuffer=new ArrayList<>();
		openFile(srcCode);
		filePtr = 0;
		this.stateMachine=new Integer[8][13];
		initMachineState();
		initSemanticActions();
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
		/*for(int state=0;state<8;state++)
		{
			this.stateMachine.put(state, new Hashtable<Character,Integer>());
		}
		this.stateMachine.get(0).put('L', 1);
		this.stateMachine.get(0).put('D', 2);
		this.stateMachine.get(0).put('_', 0);
		this.stateMachine.get(0).put('$', -1);
		this.stateMachine.get(0).put('SE', -1);
		this.stateMachine.get(0).put('{', 3);
		this.stateMachine.get(0).put('}', 0);
		this.stateMachine.get(0).put('<', 5);
		this.stateMachine.get(0).put('>', 2);
		this.stateMachine.get(0).put('=', 2);
		this.stateMachine.get(0).put('nl', 2);
		this.stateMachine.get(0).put('#', 2);
		this.stateMachine.get(0).put('TAB ', 2);
		this.stateMachine.get(0).put('otros', 2);
		*/
		
		///Hecho por matriz[estado][simb]
		this.stateMachine[0][0]=1;
		
		
		
	}
	
	private Integer getActualCharac()
	{
		return this.actualCharac;
	}
	
	private void initSemanticActions () {
		//TODO: Inicializar segun acciones
		this.stateMachine[0][0]=1;
		this.stateMachine[0][1]=1;
		this.stateMachine[0][2]=1;
		this.stateMachine[0][3]=1;
		this.stateMachine[0][4]=1;
		this.stateMachine[0][5]=1;
		this.stateMachine[0][6]=1;
		this.stateMachine[0][7]=1;
		this.stateMachine[0][8]=1;
		this.StateMachine[0][9]= ;
		this.StateMachine[0][10]= ;
		this.StateMachine[0][11]= ;
		this.StateMachine[0][12]= ;
		this.StateMachine[1][0]= ;
		this.StateMachine[1][1]= ;
		this.StateMachine[1][2]= ;
		this.StateMachine[1][3]= ;
		this.StateMachine[1][4]= ;
		this.StateMachine[1][5]= ;
		this.StateMachine[1][6]= ;
		this.StateMachine[1][7]= ;
		this.StateMachine[1][8]= ;
		this.StateMachine[1][9]= ;
		this.StateMachine[1][10]= ;
		this.StateMachine[1][11]= ;
		this.StateMachine[1][12]= ;
		this.StateMachine[2][0]= ;
		this.StateMachine[2][1]= ;
		this.StateMachine[2][2]= ;
		this.StateMachine[2][3]= ;
		this.StateMachine[2][4]= ;
		this.StateMachine[2][5]= ;
		this.StateMachine[2][6]= ;
		this.StateMachine[2][7]= ;
		this.StateMachine[2][8]= ;
		this.StateMachine[2][9]= ;
		this.StateMachine[2][10]= ;
		this.StateMachine[2][11]= ;
		this.StateMachine[2][12]= ;
		this.StateMachine[3][0]= ;
		this.StateMachine[3][1]= ;
		this.StateMachine[3][2]= ;
		this.StateMachine[3][3]= ;
		this.StateMachine[3][4]= ;
		this.StateMachine[3][5]= ;
		this.StateMachine[3][6]= ;
		this.StateMachine[3][7]= ;
		this.StateMachine[3][8]= ;
		this.StateMachine[3][9]= ;
		this.StateMachine[3][10]= ;
		this.StateMachine[3][11]= ;
		this.StateMachine[3][12]= ;
		this.StateMachine[4][0]= ;
		this.StateMachine[4][1]= ;
		this.StateMachine[4][2]= ;
		this.StateMachine[4][3]= ;
		this.StateMachine[4][4]= ;
		this.StateMachine[4][5]= ;
		this.StateMachine[4][6]= ;
		this.StateMachine[4][7]= ;
		this.StateMachine[4][8]= ;
		this.StateMachine[4][9]= ;
		this.StateMachine[4][10]= ;
		this.StateMachine[4][11]= ;
		this.StateMachine[4][12]= ;
		this.StateMachine[5][0]= ;
		this.StateMachine[5][1]= ;
		this.StateMachine[5][2]= ;
		this.StateMachine[5][3]= ;
		this.StateMachine[5][4]= ;
		this.StateMachine[5][5]= ;
		this.StateMachine[5][6]= ;
		this.StateMachine[5][7]= ;
		this.StateMachine[5][8]= ;
		this.StateMachine[5][9]= ;
		this.StateMachine[5][10]= ;
		this.StateMachine[5][11]= ;
		this.StateMachine[5][12]= ;
		this.StateMachine[6][0]= ;
		this.StateMachine[6][1]= ;
		this.StateMachine[6][2]= ;
		this.StateMachine[6][3]= ;
		this.StateMachine[6][4]= ;
		this.StateMachine[6][5]= ;
		this.StateMachine[6][6]= ;
		this.StateMachine[6][7]= ;
		this.StateMachine[6][8]= ;
		this.StateMachine[6][9]= ;
		this.StateMachine[6][10]= ;
		this.StateMachine[6][11]= ;
		this.StateMachine[6][12]= ;
		this.StateMachine[7][0]= ;
		this.StateMachine[7][1]= ;
		this.StateMachine[7][2]= ;
		this.StateMachine[7][3]= ;
		this.StateMachine[7][4]= ;
		this.StateMachine[7][5]= ;
		this.StateMachine[7][6]= ;
		this.StateMachine[7][7]= ;
		this.StateMachine[7][8]= ;
		this.StateMachine[7][9]= ;
		this.StateMachine[7][10]= ;
		this.StateMachine[7][11]= ;
		this.StateMachine[7][12]= ;


	}

	public void addChar() {
		// TODO Auto-generated method stub
		this.programBuffer.add(0, this.actualCharac);
		
	}
}
