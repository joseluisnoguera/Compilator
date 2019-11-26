package utils.semanticActions;

import logic.Lexicon;
import utils.ElementoTS;

public class SA_ConstantEnd implements SemanticAction{
	
	@Override
	public void action(Lexicon lex) {
		lex.returnCharacter();
		String lexeme = lex.getCurrentLexeme();
		double value = Double.parseDouble(lexeme);
		if (value <= 32768) {
			//agregar entero
			ElementoTS tupla = new ElementoTS(ElementoTS.CONST, "", ElementoTS.INT);
			lex.putSymbolTable(lexeme, tupla);
		} else {
			if(value > Math.pow(2, 31)) {
				//generar error que pase la parte semantica
				value = Math.pow(2, 31);
				lexeme = Double.toString((int)value);
				lex.addMsg("Línea " + lex.getNewLineCounter() + ": Warning: La constante excede el máximo posible");
			}
			if(lex.containsKey(lexeme)) 
				lex.increaseCounterSymbolTable();
			else {
				ElementoTS tupla = new ElementoTS(ElementoTS.CONST, "", ElementoTS.LONG);
				lex.putSymbolTable(lexeme, tupla);
			}
		}
	}
}