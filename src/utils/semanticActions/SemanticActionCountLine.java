package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionCountLine implements SemanticAction{
//Acción semántca asociada al conteo de salto de líneas
	public SemanticActionCountLine() {}

	@Override
	public void action(Lexicon Lex) {
		Lex.addNlCounter();
	}

}