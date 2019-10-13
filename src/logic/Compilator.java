package logic;

import java.util.Hashtable;
import utils.MsgStack;
import utils.ElementoTS;

public class Compilator {
	private static Compilator single_instance = null;
	private Hashtable<String, ElementoTS> simbTable;
	private MsgStack msgStack;
	private Lexicon lexico;
	private Parser parser;

<<<<<<< HEAD
	private static Hashtable<String, ElementoTS> simbTable;
	private static MsgStack msgStack;
	private static Lexicon lexico;
<<<<<<< HEAD
=======

>>>>>>> e2eb181... SA
	
	public Compilator()
	{
=======
	public Compilator(String path_to_source) {
		msgStack = new MsgStack();
		simbTable = new Hashtable<String, ElementoTS>();
		lexico = new Lexicon(path_to_source, msgStack, simbTable);
		parser = new Parser(lexico, simbTable, msgStack);
	}
>>>>>>> 04b8288... agregado parte del comportamiento de ventana y TODO's

	public static Compilator getInstance(String path_to_source) {
		if (single_instance == null)
			single_instance = new Compilator(path_to_source);		
		return single_instance;
	}
	
	//TODO: Hacer el método para que comience a compilar
	
	//TODO: Métodos para acceder a la tabla de simbolos, buffer de mensajes
	
	/*
	 * TODO: Método para suscribirse a la generación de token y de estructuras
	 * Una posibilidad es usar un stack para ambos y que se obtengan al final de la compilación
	 * Así no hay que meterse observer/observable
	*/
}


