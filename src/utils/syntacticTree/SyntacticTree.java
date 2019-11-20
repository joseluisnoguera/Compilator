package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.ParserVal;
import utils.RegisterTable;

public abstract class SyntacticTree extends ParserVal {
	private SyntacticTree izq;
	private SyntacticTree der;
	private String lexeme;
	private String tipo;
	protected static int contEtiquetas = 0;
	protected static String almacenamiento;
	
	public SyntacticTree(String lexeme){
		this.lexeme=lexeme;
		this.der=null;
		this.izq=null;
		tipo = ""; //Sin tipo
	}

	public String getTipo() {return this.tipo;}
	public void setTipo(String tipo) {this.tipo=tipo;}
	public SyntacticTree getHijoIzq(){return this.izq;}
	public SyntacticTree getHijoDer() {return this.der;}
	public String getElem() {return this.lexeme;} 
	public void setHijoIzq(SyntacticTree izq) {this.izq=((SyntacticTree)izq);}
	public void setHijoDer(SyntacticTree der) {this.der=(SyntacticTree)der;}
	public void setElem(String elem) {this.lexeme=elem;}

<<<<<<< HEAD
	public abstract String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep);
=======
	public abstract void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, String blankPrefix);
	public void cambiaRegistro(String regNuevo) {
		almacenamiento = regNuevo;
	}
	
	public String getAlmacenamiento() {
		return almacenamiento;
	}
	
	public void setAlmacenamiento(String dato) {
		this.almacenamiento = dato;
	}
>>>>>>> 154a393... comentario
}
