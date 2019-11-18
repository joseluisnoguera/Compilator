package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionInvalidAndReturn implements SemanticAction {
//Acción semántica asociada a la asignación incompleta, esta acción no la completa ya que se detecta en la máquina de estados

	@Override
	public void action(Lexicon Lex) {
		Lex.addMsg("Línea " + Lex.getNewLineCounter() + ": Carácter inválido");
		Lex.returnCharacter();
	}

}
