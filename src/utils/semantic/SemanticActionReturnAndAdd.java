package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionReturnAndAdd implements SemanticAction{
//Acci�n sem�ntica asociada a la devoluci�n del �ltimo car�cter le�do al buffer de programa
// y al alta en la tabla de s�mbolos (Para cadenas)
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
