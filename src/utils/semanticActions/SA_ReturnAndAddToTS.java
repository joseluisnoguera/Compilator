package utils.semanticActions;

import logic.Lexicon;
import utils.ElementoTS;

public class SA_ReturnAndAddToTS implements SemanticAction{
//Acci�n sem�ntica asociada a la devoluci�n del �ltimo car�cter le�do al buffer de programa
// y al alta en la tabla de s�mbolos (Para cadenas)
	
	SA_ReturnChar returnChar = new SA_ReturnChar();
	
	@Override
	public void action(Lexicon lex) {
		returnChar.action(lex);
		//alta en TS
		if(lex.getSymbolTable().containsKey(lex.getCurrentLexeme())) 
			lex.increaseCounterSymbolTable();
		else {
			ElementoTS tupla = new ElementoTS(ElementoTS.CAD,"","");
			lex.putSymbolTable(lex.getCurrentLexeme(), tupla);
		}
	}
}
