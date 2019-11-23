package utils.semanticActions;

import logic.Lexicon;
import utils.ElementoTS;

public class SA_CadenaEnd implements SemanticAction{
	//Acción semántica cuando se cierra una cadena con '}'
	
	@Override
	public void action(Lexicon Lex) {
		if(Lex.getSymbolTable().containsKey(Lex.getCurrentLexeme())) 
			Lex.increaseCounterSymbolTable();
		else {
			ElementoTS tupla = new ElementoTS(ElementoTS.CAD, "", "");
			tupla.setId();
			Lex.putSymbolTable(Lex.getCurrentLexeme(), tupla);
		}
	}
}
