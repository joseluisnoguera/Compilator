package utils;

public abstract class SintacticTree extends ParserVal {
	private SintacticTree izq;
	private SintacticTree der;
	private String lexeme;
	private String tipo;
	
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
	public void setHijoIzq(SintacticTree izq) {this.izq=izq;}
	public void setHijoDer(SintacticTree der) {this.der=der;}
	public void setElem(String elem) {this.lexeme=elem;}
}
