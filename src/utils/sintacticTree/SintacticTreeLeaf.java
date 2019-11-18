package utils.sintacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SintacticTreeLeaf extends SintacticTree{

	public SintacticTreeLeaf(String lex)
	{
		super(lex);
	}

	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
		return super.getElem();
	}
}

