package utils;

import logic.Lexicon;

public class SemanticActionAddChar implements SemanticAction{

	public SemanticActionAddChar()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.addCharacter();
	}

}
