package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionCountLine implements SemanticAction{
//Acci�n sem�ntca asociada al conteo de salto de l�neas
	public SemanticActionCountLine() {}

	@Override
	public void action(Lexicon Lex) {
		Lex.addNlCounter();
	}

}