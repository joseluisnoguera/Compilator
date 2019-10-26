package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionConstEnd implements SemanticAction{

	public SemanticActionConstEnd() {}
	
	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
		String lexeme = Lex.getCurrentLexeme();
		double value = new Double(lexeme).doubleValue();
		if (value <= 32768) {
			//agregar entero
			ElementoTS tupla = new ElementoTS("CTE","", "int");
			Lex.putSymbolTable(lexeme, tupla);
		} else {
			if(value > Math.pow(2, 31)) {
				//generar error que pase la parte semantica
				value = Math.pow(2, 31);
				lexeme = Double.toString((int)value);
				Lex.addMsg("Línea " + Lex.getNewLineCounter() + ": Warning: La constante excede el máximo posible");
			}
			if(Lex.containsKey(lexeme)) 
				Lex.increaseCounterSymbolTable();
			else {
				ElementoTS tupla = new ElementoTS("CTE","", "long");
				Lex.putSymbolTable(lexeme, tupla);
			}
		}
	}
}