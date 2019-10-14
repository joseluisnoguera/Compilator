package utils.semantic;

import logic.Lexicon;

public class SemanticActionReturnCharacter implements SemanticAction{
//Acción semántica asociado a la devolución del último carácter leído al buffer de programa
	public SemanticActionReturnCharacter() {}

	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
	}

}