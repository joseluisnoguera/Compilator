package utils.semantic;

import logic.Lexicon;

public class SemanticActionReturnAndUp implements SemanticAction{

	public SemanticActionReturnAndUp()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
		//alta en TS
	}
}
