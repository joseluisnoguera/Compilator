package utils.semantic;

import logic.Lexicon;

public class SemanticActionReturnCharacter implements SemanticAction{

	public SemanticActionReturnCharacter()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.returnCharacter();

	}

}