package utils;

public class ElementoTS{
	private String value; //TODO: Para valores númericos (CONST o PR) va a dar problemas de rango con long --> se lo cambia a String
	private String tipoToken; //ID CONST PR CAD
	private String tipoAtributo; //int long
	private int cantidad;
	private boolean declaracion;
	private String estructuraID;
	private int cSize; //tamaño de la coleccion
	private String elemsColecc;


	


	public ElementoTS(String tipoToken, String value, String tipoAtributo) {
		this.value=value;
		this.tipoToken = tipoToken;
		this.tipoAtributo = tipoAtributo;
		cantidad = 1;
		this.declaracion=false;
		this.estructuraID=null;
		this.cSize = 0;
	}

	public String getElemsColecc() {return this.elemsColecc;}
	
	public void setElemsColecc(String elems) {this.elemsColecc=elems;}

	public String getValue(){ return value; }

	public String getTipoAtributo(){ return tipoAtributo; }
	
	public String getTipoToken() { return tipoToken; }
	
	public int getCantidad(){ return cantidad; }
	
	public int getCSize() {return cSize;}
	
	public void increaseCounter(){ cantidad = cantidad + 1; }
	
	public void decreaseCounter(){ cantidad = cantidad - 1; }
	
	public void setDeclaracion(boolean dato) {
		this.declaracion=dato;
	}
	
	public void setCSize(String dato) {this.cSize = Integer.valueOf(dato);}
	
	public boolean getDeclaracion(){return this.declaracion;}
	
	public void setTipoAtributo(String tipo){ tipoAtributo = tipo; }
	
	public void setEstructuraID(String tipo) { this.estructuraID=tipo;}
	
	public String getEstructuraID() {return this.estructuraID;}
}