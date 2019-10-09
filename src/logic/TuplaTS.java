package logic;

public class TuplaTS{
	private int tokenValue;
	private String tipoAtributo;
	private int cantidad;

	public TuplaTS(int tokenValue){
		this.tokenValue = tokenValue;
	}
	
	public TuplaTS(int tokenValue, String tipoAtributo){
		this.tokenValue = tokenValue;
		this.tipoAtributo = tipoAtributo;
		this.cantidad=0;
	}

	public int getTokenValue(){
		return tokenValue;
	}

	public String getTipoAtributo(){
		return tipoAtributo;
	}
	public int getCantidad(){
		return this.cantidad;
	}
	public void setCantidad(int cant){
		this.cantidad=this.cantidad + cant;
	}
	public void setTipoAtributo(String tipo){
		this.tipoAtributo = tipo;
	}
}