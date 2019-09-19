package utils;

import logic.Lexicon;

public class SemanticActionFinID implements SemanticAction{

	@Override
	public void action(Lexicon Lex) {
		// TODO Auto-generated method stub
		Lex.returnCharacter();
		String lexeme=Lex.getActualLexeme();
		if(lexeme.length()>25)
		{
			String auxLex = null;
			int index=0;
			while(auxLex.length()==25)
			{
				auxLex=auxLex + lexeme.charAt(index);
				index++;
			}
			//mandamos warning que se le trunco
		}
		//Hacer parte de TS --> habria que mandar por parametro la ts
	}

	
	
}
