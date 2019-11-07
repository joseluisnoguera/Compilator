package utils;

public class SintacticTreeUnary extends SintacticTree{

	public SintacticTreeUnary(String lexeme, SintacticTree nodo) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodo);
	}

	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm) {
		comAssembler.addMsg("invoke StdOut, addr _" + super.getElem());
		return "";
	}
}
