package utils.semanticActions;

import logic.Lexicon;

public class SA_ReturnChar implements SemanticAction{
//Acci�n sem�ntica asociado a la devoluci�n del �ltimo car�cter le�do al buffer de programa

	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
	}

}