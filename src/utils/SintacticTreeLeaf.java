package utils;

public class SintacticTreeLeaf extends SintacticTree{

	public SintacticTreeLeaf(String lex)
	{
		super(lex);
	}
	
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm) {
		return "_" + super.getElem();
	}
}

