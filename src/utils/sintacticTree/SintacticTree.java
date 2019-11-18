package utils.sintacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.ParserVal;
import utils.RegisterTable;

public abstract class SintacticTree extends ParserVal {
	private SintacticTree izq;
	private SintacticTree der;
	private String lexeme;
	private String tipo;
	protected static int contEtiquetas = 0;

	public SintacticTree(String lexeme){
		this.lexeme=lexeme;
		this.der=null;
		this.izq=null;
	}

	public String getTipo() {return this.tipo;}
	public void setTipo(String tipo) {this.tipo=tipo;}
	public SintacticTree getHijoIzq(){return this.izq;}
	public SintacticTree getHijoDer() {return this.der;}
	public String getElem() {return this.lexeme;} 
	public void setHijoIzq(SintacticTree izq) {this.izq=((SintacticTree)izq);}
	public void setHijoDer(SintacticTree der) {this.der=(SintacticTree)der;}
	public void setElem(String elem) {this.lexeme=elem;}

	public abstract String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable);
}
