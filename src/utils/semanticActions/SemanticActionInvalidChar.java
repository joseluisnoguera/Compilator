package utils.semanticActions;

import logic.Lexicon;

public class SemanticActionInvalidChar implements SemanticAction{
//Acción semántica asociada a la detección de carácteres inválidos, con aviso al programador
	public SemanticActionInvalidChar() {}
	
	@Override
	public void action(Lexicon Lex) {
		Lex.addMsg("Línea " + Lex.getNewLineCounter() + ": Carácter inválido " + Lex.getCurrentCharacter());
	}

}