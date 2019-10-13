package utils.semantic;

import logic.Lexicon;

public class SemanticActionInvalidCharact implements SemanticAction{

	public SemanticActionInvalidCharact()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		int nroLinea=Lex.getNLCounter();
		Lex.addMsg("Caracter invalido"+nroLinea);//se registra el error, se avisa, y se realiza un "modo panico" hasta el proximo token de referencia (por ejemplo, hasta que detecte un espacio, un punto y coma, etc)
	}

}