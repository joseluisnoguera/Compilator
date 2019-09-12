package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Queue;

import utils.MsgStack;
import utils.SemanticAction;
import utils.Token;

public class Lexicon {
	private final int END_OF_FILE = -1;
	private int filePtr;
	private Queue<Integer> programBuffer;
	private int nlCounter;
	private MsgStack msgStack;
	private Hashtable<Character, Integer> stateMachine;
	private Hashtable<Character, SemanticAction> semanticAction;
	
	
	
	public Lexicon(String srcCode, MsgStack msgStack) {
		this.nlCounter = 0;
		this.msgStack = msgStack;
		openFile(srcCode);
		filePtr = 0;
		initMachineState();
		initSemanticActions();
	}
	
	public int getNLCounter() { return nlCounter; }
	
	public Token getNewToken() {
		String lexeme; //El lexema y el conteo de nl debe ir en las AS (¿cómo ponerlo ahí?)
		//TODO: Itera en la máquina de estados hasta tener un token
		
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
	}
	
	private void initSemanticActions () {
		//TODO: Inicializar segun acciones
	}
}
