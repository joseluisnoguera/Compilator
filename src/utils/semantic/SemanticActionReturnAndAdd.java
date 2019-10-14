package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionReturnAndAdd implements SemanticAction{
//Acción semántica asociada a la devolución del último carácter leído al buffer de programa
// y al alta en la tabla de símbolos (Para cadenas)
	public SemanticActionReturnAndAdd() {}
	
	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
		//alta en TS
		if(Lex.getSymbolTable().containsKey(Lex.getCurrentLexeme())) 
			Lex.increaseCounterSymbolTable();
		else {
			ElementoTS tupla = new ElementoTS("CAD");
			Lex.putSymbolTable(Lex.getCurrentLexeme(), tupla);
		}
	}
}
