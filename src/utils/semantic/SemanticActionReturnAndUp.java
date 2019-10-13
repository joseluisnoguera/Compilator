package utils.semantic;

import logic.Lexicon;
import utils.ElementoTS;

public class SemanticActionReturnAndUp implements SemanticAction{

	public SemanticActionReturnAndUp()
	{
		
	}
	@Override
	public void action(Lexicon Lex) {
		Lex.returnCharacter();
		//alta en TS
		if(Lex.getSimbTable().containsKey(Lex.getActualLexeme())) 
			Lex.setCantSimbTable(1);
		else
		{
			ElementoTS tupla = new ElementoTS(274, "CAD");
		
			Lex.altaSimbTable(Lex.getActualLexeme(), tupla);
		}
		}
}
