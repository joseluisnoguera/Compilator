package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionCADend implements SemanticAction{

	public SemanticActionCADend()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		ElementoTS tupla = new ElementoTS(274, "CAD");
		Lex.altaSimbTable(Lex.getActualLexeme(), tupla);
	}

	
}
