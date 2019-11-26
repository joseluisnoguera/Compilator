package logic;

import java.util.Hashtable;
import java.util.List;

import utils.MsgStack;
import utils.RegisterTable;
import utils.syntacticTree.ST_Control;
import utils.AssemblerGenerator;
import utils.ElementoTS;

/*
 *TODO: Antes de entregar sacar las ventanas de token y estructura sintáctica
<<<<<<< HEAD
 *TODO: En recorre árbol que agregue blancos según profundidad
 *TODO: Llevar códigos de errores dinámicos y la funcion de print al objeto que junta todo el assembler
 *TODO: Revisar Nodo common
 *TODO: Revisar si gramática es correcta
 *TODO: Función para pedir registros específicos (CWD usa EAX, la multiplicación usa EDX y EAX), el genérico devuelve cualquiera menos EAX
 *TODO: Revisar que todos los recorre árbol liberen los registros que usaron
<<<<<<< HEAD
 *TODO: Dejar siempre libre EAX
 *TODO: Agregar tipo puntero, y la colección a la que pertenece en TS
=======
 *TODO: Dejar siempre libre EAX (PREGUNTAR SI ES CORRECTO, EN CASO TAL HABRIA QUE DEJAR LIBRE DX TAMBIEN)
>>>>>>> 9acbfaf... comentario
=======
 *TODO: Revisar Nodo common
 *TODO: Revisar si gramática es correcta
>>>>>>> 1375c5c... arreglos varios
 */

public class Compilator {
	private static Compilator single_instance = null;
	private Hashtable<String, ElementoTS> symbolTable;
	private MsgStack msgStack, semanticStructStack, tokenStack;
	private Lexicon lexicon;
	private Parser parser;
	private ST_Control nodoRaiz;
	private RegisterTable registros;
	private MsgStack assemblerCode;
	private MsgStack syntacticTree;
	private boolean hasErrors;


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
		assemblerCode = new MsgStack();
		syntacticTree = new MsgStack();
		symbolTable = new Hashtable<String, ElementoTS>();
		ElementoTS.resetIdGenerator();
		lexicon = new Lexicon(programBuffer, msgStack, tokenStack, symbolTable);
		parser = new Parser(lexicon, symbolTable, msgStack, semanticStructStack);
		hasErrors = true;
	}
>>>>>>> 04b8288... agregado parte del comportamiento de ventana y TODO's

	public static Compilator getInstance(List<Integer> programBuffer) {
		single_instance = new Compilator(programBuffer); //Lo usamos así para que cree siempre uno nuevo, así renueva el program buffer
		return single_instance;
	}

	public void compilate() {
		int i = parser.yyparse();
		if (i == 0) {
			System.out.println("Parser correcto");
<<<<<<< HEAD
			//TODO: Ver si no hubieron errores
			nodoRaiz = ((SyntacticTreeCtrl)parser.getRaiz());
			nodoRaiz.recorreArbol(registros, assemblerCode, syntaticTree, symbolTable, "");
//			assemblerCode = AssemblerGenerator.getAssembler(symbolTable, assemblerCode);
=======
			if (!parser.hasErrors()) {
				hasErrors = false;
				nodoRaiz = ((ST_Control)parser.getRaiz());
				registros = new RegisterTable();
				nodoRaiz.recorreArbol(registros, assemblerCode, syntacticTree, symbolTable, "");
				assemblerCode = AssemblerGenerator.getAssembler(symbolTable, assemblerCode);
<<<<<<< HEAD
			}
>>>>>>> 1375c5c... arreglos varios
=======
			} else
				hasErrors = true;
>>>>>>> 67de6b7... _
		}
		else
			System.out.println("Error en parser");
	}
	
	public boolean hasErrors() { return hasErrors; }

	public MsgStack getMsgStack() { return msgStack; }

	public MsgStack getTokenStack() { return tokenStack; }

	public MsgStack getSemanticStructStack() { return semanticStructStack; }
	
	public MsgStack getAssemblerCode() { return assemblerCode; }
	
	public MsgStack getSyntacticTree() { return syntacticTree; }

	public Hashtable<String, ElementoTS> getSymbTable() { return symbolTable; }
}


