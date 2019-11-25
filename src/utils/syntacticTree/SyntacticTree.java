package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.ParserVal;
import utils.RegisterTable;

public abstract class SyntacticTree extends ParserVal {
	private String blankSpace = "  "; // Para poder cambiar el espaciado en el árbol de manera más rápida
	private SyntacticTree izq;
	private SyntacticTree der;
	private String lexeme;
<<<<<<< HEAD
	private String tipo;
<<<<<<< HEAD
	protected static int contEtiquetas = 0;
	protected static String almacenamiento;
	
	public SyntacticTree(String lexeme){
		this.lexeme=lexeme;
		this.der=null;
		this.izq=null;
		tipo = ""; //Sin tipo
=======
=======
	private String type;
>>>>>>> fde7cdb... varios
	protected String almacenamiento;
	
	public SyntacticTree(String lexeme){
		this.lexeme = lexeme;
		der = null;
		izq = null;
<<<<<<< HEAD
		tipo = ""; //Sin tipo por defecto
>>>>>>> 51f241d... arreglos varios
=======
		type = ""; //Sin tipo por defecto
>>>>>>> fde7cdb... varios
	}

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public SyntacticTree getHijoIzq(){ return izq; }
	public SyntacticTree getHijoDer() { return der; }
	public String getElem() { return lexeme; } 
	public void setHijoIzq(SyntacticTree izq) { this.izq = izq; }
	public void setHijoDer(SyntacticTree der) { this.der = der; }
	public void setElem(String elem) { lexeme = elem; }

<<<<<<< HEAD
	public abstract String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep);
=======
	public abstract void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, String blankPrefix);
<<<<<<< HEAD
	public void cambiaRegistro(String regNuevo) {
		almacenamiento = regNuevo;
	}
=======
	
<<<<<<< HEAD
	public void cambiaRegistro(String almacenamientoNuevo) { almacenamiento = almacenamientoNuevo; }
>>>>>>> 51f241d... arreglos varios
	
=======
>>>>>>> 0fcca1b... varios
	public String getAlmacenamiento() { return almacenamiento; }
	
<<<<<<< HEAD
	public void setAlmacenamiento(String dato) {
		this.almacenamiento = dato;
	}
>>>>>>> 154a393... comentario
}
=======
	public void setAlmacenamiento(String almacenamiento) { this.almacenamiento = almacenamiento; }
	
	public boolean isVariableOrConst() { return (isVariable() || isConstant()); }

	public boolean isVariable() { return getAlmacenamiento().charAt(0) == '_'; }
	
	public boolean isConstant() { return ((int)(getAlmacenamiento().charAt(0)) >= 48 && (int)(getAlmacenamiento().charAt(0)) <= 57); }
	
	public String getBlankSpace() { return blankSpace; }
}
>>>>>>> 51f241d... arreglos varios
