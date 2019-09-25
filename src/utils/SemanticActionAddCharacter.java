package utils;

import logic.Lexicon;

public class SemanticActionAddCharacter implements SemanticAction{

	public SemanticActionAddCharacter()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.addCharacter();
	}

}
