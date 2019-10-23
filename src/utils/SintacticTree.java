package utils;

public class SintacticTree {
	private SintacticTree izq;
	private SintacticTree der;
	private ElementoTS elem;
	
	public SintacticTree(){}
	
	public SintacticTree getHijoIzq(){return this.izq;}
	public SintacticTree getHijoDer() {return this.der;}
	public ElementoTS getElem() {return this.elem;} 
	public void setHijoIzq(SintacticTree izq) {this.izq=izq;}
	public void setHijoDer(SintacticTree der) {this.der=der;}
	public void setElem(ElementoTS elem) {this.elem=elem;}
}
