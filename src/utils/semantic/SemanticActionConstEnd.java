package utils.semantic;

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
		}else if(value <= (2^31)){
			//agregar long
		}else{
			//generar error que pase la parte semantica
			value = (2^31);
			//agregar long
		}
	}

}
