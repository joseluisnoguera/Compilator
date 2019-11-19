package logic;

import java.util.Hashtable;
import java.util.List;

import utils.MsgStack;
import utils.RegisterTable;
import utils.syntacticTree.SyntacticTreeCtrl;
import utils.ElementoTS;

/*
 *TODO: En recorre árbol que guarde el mismo árbol para mostrar
 *TODO: En compilator que junte todo el assembler y agregue lo faltante
 *TODO: Ver como hacer código assembler para print
 *TODO: Llevar códigos de errores dinámicos y la funcion de print al objeto que junta todo el assembler
 *TODO: Revisar Nodo common
 *TODO: Revisar si gramática es correcta
 *TODO: Función para pedir registros específicos (CWD usa EAX, la multiplicación usa EDX y EAX), el genérico devuelve cualquiera menos EAX
 *TODO: Revisar que todos los recorre árbol liberen los registros que usaron
 *TODO: Dejar siempre libre EAX
 */

public class Compilator {
	private static Compilator single_instance = null;
	private Hashtable<String, ElementoTS> symbolTable;
	private MsgStack msgStack, semanticStructStack, tokenStack;
	private Lexicon lexicon;
	private Parser parser;
	private SyntacticTreeCtrl nodoRaiz;
	private RegisterTable registros;
	private MsgStack codAssembler;
	private MsgStack syntaticTree;


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
		parser = new Parser(lexicon, symbolTable, msgStack, semanticStructStack);
	}
>>>>>>> 04b8288... agregado parte del comportamiento de ventana y TODO's

	public static Compilator getInstance(List<Integer> programBuffer) {
		single_instance = new Compilator(programBuffer); //Lo usamos así para cree siempre uno nuevo, así renueva el program buffer
		return single_instance;
	}

	public void compilate() {
		int i = parser.yyparse();
		if (i == 0) {
			System.out.println("Parser correcto");
			nodoRaiz = ((SyntacticTreeCtrl)parser.getRaiz());
			nodoRaiz.recorreArbol(registros, codAssembler, syntaticTree, symbolTable);
		}
		else
			System.out.println("Error en parser");
	}

	public MsgStack getMsgStack() { return msgStack; }

	public MsgStack getTokenStack() { return tokenStack; }

	public MsgStack getSemanticStructStack() { return semanticStructStack; }
	
	public MsgStack getCodAssembler() { return codAssembler; }
	
	public MsgStack getSyntacticTree() { return syntaticTree; }

	public Hashtable<String, ElementoTS> getSimbTable() { return symbolTable; }
}


