package utils;

public class Token {
	private int tokenValue;
	private String lexeme;
	
	public Token(int tokenValue, String lexeme) { 
		this.tokenValue = tokenValue;
		this.lexeme = lexeme;
	}

	public int getTokenValue() { return tokenValue;	}

	public String getLexeme() {	return lexeme; }
}
