package utils.semanticActions;

import logic.Lexicon;

public class SA_InvalidAndReturnChar implements SemanticAction {
//Acci�n sem�ntica asociada a la asignaci�n incompleta, esta acci�n no la completa ya que se detecta en la m�quina de estados

	SA_InvalidChar invalidChar = new SA_InvalidChar();
	
	@Override
	public void action(Lexicon lex) {
		invalidChar.action(lex);
		lex.returnCharacter();
	}

}
