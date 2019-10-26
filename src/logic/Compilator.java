package logic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import utils.MsgStack;
import utils.SintacticTree;
import utils.ElementoTS;

/*
 * Cambios: Pase la creación de estructuras al constructor así no están como estáticas, quedando más acorde con el patrón singletón
 * Agregue los stack para obtener los Token del léxico y las estructuras semánticas del Parser
 */

public class Compilator {
	private static Compilator single_instance = null;
	private Hashtable<String, ElementoTS> symbolTable;
	private MsgStack msgStack, semanticStructStack, tokenStack;
	private ArrayList<SintacticTree> sintacTree;
	private Lexicon lexicon;
	private Parser parser;

<<<<<<< HEAD
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
=======
	public Compilator(List<Integer> programBuffer) {
>>>>>>> 1ade0f6... Funcion de carga de datos en paneles de la ventana
		msgStack = new MsgStack();
		semanticStructStack = new MsgStack();
		tokenStack = new MsgStack();
		symbolTable = new Hashtable<String, ElementoTS>();
		lexicon = new Lexicon(programBuffer, msgStack, tokenStack, symbolTable);
		this.sintacTree=new ArrayList<>();
		parser = new Parser(lexicon, symbolTable, msgStack, semanticStructStack, sintacTree);

	}
>>>>>>> 04b8288... agregado parte del comportamiento de ventana y TODO's

	public static Compilator getInstance(List<Integer> programBuffer) {
		single_instance = new Compilator(programBuffer); //Lo usamos así para cree siempre uno nuevo, así renueva el program buffer
		return single_instance;
	}
	
	public void compilate() {
		int i = parser.yyparse();
		if (i == 0)
			System.out.println("Parser correcto");
		else
			System.out.println("Error en parser");
	}
	
	public MsgStack getMsgStack() { return msgStack; }
	
	public MsgStack getTokenStack() { return tokenStack; }
	
	public MsgStack getSemanticStructStack() { return semanticStructStack; }
	
	public Hashtable<String, ElementoTS> getSimbTable() { return symbolTable; }
}


