package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionConstEnd implements SemanticAction{

	public SemanticActionConstEnd()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.addCharacter();
		String lexeme = Lex.getActualLexeme();
		double value = new Double(lexeme).doubleValue();
		if (value <= 32768){
			//agregar entero
			ElementoTS tupla = new ElementoTS(260, "CTE", "int");
			 Lex.altaSimbTable(lexeme, tupla);
		}else 
		{
			if(value > (2^31)){
				//generar error que pase la parte semantica
				value = (2^31);
				lexeme = Double.toString(value);
			}
		if(Lex.getSimbTable().containsKey(lexeme)) 
			Lex.setCantSimbTable(1);
		else
		{
		ElementoTS tupla = new ElementoTS(260, "CTE", "long");
		 Lex.altaSimbTable(lexeme, tupla);
	}
	}
	}
}
