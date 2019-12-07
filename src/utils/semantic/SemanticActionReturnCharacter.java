package utils.semantic;

import logic.Lexicon;

public class SemanticActionReturnCharacter implements SemanticAction{
//Acci�n sem�ntica asociado a la devoluci�n del �ltimo car�cter le�do al buffer de programa
	public SemanticActionReturnCharacter() {}

	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
	}

}