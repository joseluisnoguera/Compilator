package utils;

public class ElementoTS{
	private String value; //Para valores númericos (CONST o PR)
	private String tipoToken; //ID CONST PR CAD
	private String tipoAtributo; //int long
	private int cantidad; //Cantidad de repeticiones de la variable/constante
	private boolean declarada; //Para control de declaración de variables
	private String estructuraID; // Define en ID si es colección o variable
	private int cSize; // Tamaño de la coleccion
	private int cSizeBytes; // Tamaño de la colección en bytes para no multiplicar en el foreach
	private String elemsCollection; //Elementos de la colección separados por ','
	private static int idCounter = 0; //Generador de ID único para cadenas
	private int id; // Id para identificar variables
	private boolean isPointer;

	// Tipos de variables:
	public static final String INT = "int";
	public static final String LONG = "long";
	// Tipos de tokens:
	public static final String ID = "id";
	public static final String CONST = "const";
	public static final String PR = "pr";
	public static final String CAD = "cad";
	// Tipo de ID
	public static final String VAR = "var";
	public static final String COL = "collection";

	public ElementoTS(String tipoToken, String value, String tipoAtributo) {
		this.value=value;
		this.tipoToken = tipoToken;
		this.tipoAtributo = tipoAtributo;
		cantidad = 1;
<<<<<<< HEAD
		declarada=false;
		estructuraID=null;
		cSize = 0;
=======
		declarada = false;
		estructuraID = "";
<<<<<<< HEAD
		cSize = -1;
>>>>>>> 67de6b7... _
		id = -1;
		cSizeBytes = -1;
<<<<<<< HEAD
		isPointer=false;
=======
=======
		cSize = 0;
		id = -1;
		cSizeBytes = 0;
>>>>>>> 944a2e1... nothing
		isPointer = false;
<<<<<<< HEAD
		estructuraID = "";
>>>>>>> 6bb5a8f... _
=======
		elemsCollection = "";
>>>>>>> 67de6b7... _
	}

	public String getElemsCollection() {return elemsCollection;}

	public Boolean isPointer() { return isPointer; }

	public void setHasPointer() { isPointer = true; }

	public void setElemsCollection(String elems) { elemsCollection = elems;}

	public String getValue(){ return value; }

	public String getTipoAtributo(){ return tipoAtributo; }

	public String getTipoToken() { return tipoToken; }

	public int getCantidad(){ return cantidad; }

	public int getCSize() { return cSize; }

	public void increaseCounter(){ cantidad = cantidad + 1; }

	public void decreaseCounter(){ cantidad = cantidad - 1; }

	public void setDeclarada(boolean dato) {
		this.declarada=dato;
	}

	public void setCSize(int dato) { cSize = dato;}

	public boolean isDeclarada(){ return declarada; }

	public void setTipoAtributo(String tipo){ 
		tipoAtributo = tipo;
		if (tipo == INT)
			cSizeBytes = cSize * 2;
		else //sino es long
			cSizeBytes = cSize * 4;
	}

	public void setEstructuraID(String estructura) { estructuraID = estructura; }

	public String getEstructuraID() { return estructuraID; }

	public int getCSizeBytes() { return cSizeBytes; }

	public void setId() {
		id = idCounter;
		idCounter++;
	}

	// Para obtener id único para cadenas
/*	public static int getNewId() {
		idCounter++;
		return idCounter-1;
<<<<<<< HEAD
	}*/
	
=======
	}

>>>>>>> bbb6d5a... commit para pull
	public int getId() { return id; }
}