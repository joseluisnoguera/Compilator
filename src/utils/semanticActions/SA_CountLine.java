package utils.semanticActions;

import logic.Lexicon;

public class SA_CountLine implements SemanticAction{
//Acci�n sem�ntca asociada al conteo de salto de l�neas

	@Override
	public void action(Lexicon Lex) {
		Lex.addNlCounter();
	}

}