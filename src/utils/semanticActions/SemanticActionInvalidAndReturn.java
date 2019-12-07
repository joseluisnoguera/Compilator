package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionInvalidAndReturn implements SemanticAction {
//Acci�n sem�ntica asociada a la asignaci�n incompleta, esta acci�n no la completa ya que se detecta en la m�quina de estados

	@Override
	public void action(Lexicon Lex) {
		Lex.addMsg("L�nea " + Lex.getNewLineCounter() + ": Car�cter inv�lido");
		Lex.returnCharacter();
	}

}
