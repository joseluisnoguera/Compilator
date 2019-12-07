package utils.semanticActions;

import logic.Lexicon;

public class SA_InvalidChar implements SemanticAction{
//Acci�n sem�ntica asociada a la detecci�n de car�cteres inv�lidos, con aviso al programador
	
	@Override
	public void action(Lexicon Lex) {
		Lex.addMsg("L�nea " + Lex.getNewLineCounter() + ": Car�cter inv�lido.");
	}

}