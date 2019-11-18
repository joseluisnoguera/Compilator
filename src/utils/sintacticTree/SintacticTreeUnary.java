package utils.sintacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SintacticTreeUnary extends SintacticTree{

	public SintacticTreeUnary(String lexeme, SintacticTree nodo) {
		super(lexeme);
		super.setHijoIzq(nodo);
	}

	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
		comAssembler.addMsg("invoke StdOut, addr " + super.getElem());
		return "";
	}
}
