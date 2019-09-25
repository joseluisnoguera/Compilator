package logic;

public class TuplaTS{
	private int tokenValue;
	private String tipoAtributo;

	public TuplaTS(int tokenValue){
		this.tokenValue = tokenValue;
	}
	
	public TuplaTS(int tokenValue, String tipoAtributo){
		this.tokenValue = tokenValue;
		this.tipoAtributo = tipoAtributo;
	}

	public int getTokenValue(){
		return tokenValue;
	}

	public String getTipoAtributo(){
		return tipoAtributo;
	}
}