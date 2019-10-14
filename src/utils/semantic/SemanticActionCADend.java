package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionCADend implements SemanticAction{
	//Acción semántica cuando se cierra una cadena con '}'
	
	public SemanticActionCADend() { }
	
	@Override
	public void action(Lexicon Lex) {
		if(Lex.getSymbolTable().containsKey(Lex.getCurrentLexeme())) 
			Lex.increaseCounterSymbolTable();
		else {
			ElementoTS tupla = new ElementoTS("CAD", "cadena");
			Lex.putSymbolTable(Lex.getCurrentLexeme(), tupla);
		}
	}
}
