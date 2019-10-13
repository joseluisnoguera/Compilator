package utils.semantic;

import java.util.Hashtable;

import logic.Lexicon;
import utils.ElementoTS;

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
			Lex.addMsg("Warning: se trunco el valor del ID por exceder de los caracteres permitidos");
		}
		
		//Hacer parte de TS --> habria que mandar por parametro la ts
		 ElementoTS tupla = new ElementoTS(259, "ID");
		 Lex.altaSimbTable(lexeme, tupla);
	}

	
	
}
