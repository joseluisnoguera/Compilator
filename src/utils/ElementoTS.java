package utils;

public class ElementoTS{
	private int value; //TODO: Para valores n�mericos (CONST o PR) va a dar problemas de rango con long
	private String tipoToken; //ID CONST PR
	private String tipoAtributo; //int long
	private int cantidad;
	private boolean declaracion;

	public ElementoTS(String tipoToken, String tipoAtributo){
		//Para CTEs
		this.tipoToken = tipoToken;
		this.tipoAtributo = tipoAtributo;
		cantidad = 1;
		this.declaracion=false;
	}
	
	public ElementoTS(int value, String tipoToken){
		//Para palabras reservadas en l�xico
		this.value = value;
		this.tipoToken = tipoToken;
		tipoAtributo = null;
		cantidad = 1;
		this.declaracion=false;
	}
	
	public ElementoTS(String tipoToken){
		//Para los ID, luego Parser debe setear el tipo y su valor
		this.tipoToken = tipoToken;
		tipoAtributo = null;
		cantidad = 1;
		this.declaracion=false;
	}

	public int getValue(){ return value; }

	public String getTipoAtributo(){ return tipoAtributo; }
	
	public String getTipoToken() { return tipoToken; }
	
	public int getCantidad(){ return cantidad; }
	
	public void increaseCounter(){ cantidad = cantidad + 1; }
	
	public void decreaseCounter(){ cantidad = cantidad - 1; }
	
	public void setDeclaracion() {
		if(!this.declaracion)
			this.declaracion=true;
		else
			this.declaracion=false;
	}
	
	public boolean getDeclaracion(){return this.declaracion;}
	
	public void setTipoAtributo(String tipo){ tipoAtributo = tipo; }
}