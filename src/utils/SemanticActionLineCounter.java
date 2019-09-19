package utils;

import logic.Lexicon;

public class SemanticActionLineCounter implements SemanticAction{

	public SemanticActionLineCounter()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.addNlCounter();//funcion que sume uno en el contador de lineas
	}

}