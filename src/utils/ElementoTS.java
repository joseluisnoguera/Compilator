package utils;

public class ElementoTS{
	private int value; //TODO: Para valores númericos (CONST o PR) va a dar problemas de rango con long
	private String tipoToken; //ID CONST PR
	private String tipoAtributo; //int long
	private int cantidad;

	public ElementoTS(int value, String tipoToken, String tipoAtributo){
		this.value = value;
		this.tipoToken = tipoToken;
		this.tipoAtributo = tipoAtributo;
		cantidad = 1;
	}
	
	public ElementoTS(int value, String tipoToken){
		this.value = value;
		this.tipoToken = tipoToken;
		tipoAtributo = null;
		cantidad = 1;
	}

	public int getValue(){ return value; }

	public String getTipoAtributo(){ return tipoAtributo; }
	
	public String getTipoToken() { return tipoToken; }
	
	public int getCantidad(){ return cantidad; }
	
	public void setCantidad(int cant){ cantidad = cantidad + cant; }
	
	public void setTipoAtributo(String tipo){ tipoAtributo = tipo; }
}