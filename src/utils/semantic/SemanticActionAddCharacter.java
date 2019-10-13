package utils.semantic;

import logic.Lexicon;

public class SemanticActionAddCharacter implements SemanticAction{

	@Override
	public void action(Lexicon Lex) {
		Lex.addCharacter();
	}

}
