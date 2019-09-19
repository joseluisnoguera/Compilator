package utils;

import logic.Lexicon;

public class SemanticActionReturnChar implements SemanticAction{

	public SemanticActionReturnChar()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.returnCharacter();
		//agregar puntero a la tl
	}

}