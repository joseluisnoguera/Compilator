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
	
	private void initSemanticActions () {
		//TODO: Inicializar segun acciones
	}
}
