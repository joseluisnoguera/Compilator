package utils;

import logic.Lexicon;

public class SemanticActionConstEnd implements SemanticAction{

	public SemanticActionConstEnd()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.addCharacter();
		String lexemaActual = Lex.getActualLexeme();
		double value = new Double(lexemaActual).doubleValue();
		if (value <= 32768){
			//agregar entero
		}else if(value <= 2147483648){
			//agregar long
		}else{
			//generar error que pase la parte semantica
			value = 2147483648;
			//agregar long
		}
	}

}
