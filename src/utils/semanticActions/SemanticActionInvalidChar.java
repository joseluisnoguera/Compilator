package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionInvalidChar implements SemanticAction{
//Acci�n sem�ntica asociada a la detecci�n de car�cteres inv�lidos, con aviso al programador
	public SemanticActionInvalidChar() {}
	
	@Override
	public void action(Lexicon Lex) {
		Lex.addMsg("L�nea " + Lex.getNewLineCounter() + ": Car�cter inv�lido " + Lex.getCurrentCharacter());
	}

}