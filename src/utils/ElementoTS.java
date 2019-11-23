package utils;

public class ElementoTS{
<<<<<<< HEAD
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
=======
	private String value; 				// Para valores númericos que requieren ser guardados (CONST o PR)
	private String tokenClass; 			// ID CONST PR CAD
	private String variableType; 		// int long
	private int variableRepatitions; // Cantidad de repeticiones de la variable/constante
	private boolean declared; 		  	// Para control de declaración de variables
	private String identifierClass;	  	// Define si un ID es una colección (COL) o una variable (VAR)
	private int cSize; 				  	// Tamaño de la coleccion
	private int cSizeBytes; 		  	// Tamaño de la colección en bytes
	private String elemsCollection;   	//Elementos de la colección separados por ','
	private static int idCounter = 0; 	//Generador de ID único para cadenas
	private int idCadena; 			  	// Id para identificar cadenas
	private boolean isPointer; 		  	// Define si un ID variable es puntero (32 bits sin tipo)

			//// Tipos de variables ////
	public static final String INT = "int";
	public static final String LONG = "long";
			//// Clases de tokens ////
	public static final String ID = "Identifier";
	public static final String CONST = "Constant";
	public static final String PR = "Reserved word";
	public static final String CAD = "Cadena";
			//// Clases de ID ////
	public static final String VAR = "Variable";
	public static final String COL = "Collection";

	public ElementoTS(String tokenClass, String value, String variableType) {
		this.value = value;
		this.tokenClass = tokenClass;
		this.variableType = variableType;
		variableRepatitions = 1;
		declared = false;
		identifierClass = "";
>>>>>>> 0fcca1b... varios
		cSize = 0;
		idCadena = -1;
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

	public String getVariableType(){ return variableType; }

	public String getTokenClass() { return tokenClass; }

	public int getVariableRepetitions(){ return variableRepatitions; }

	public int getCSize() { return cSize; }

	public void increaseVariableRepetitions(){ variableRepatitions = variableRepatitions + 1; }

	public void decreaseVariableRepetitions(){ variableRepatitions = variableRepatitions - 1; }

	public void setDeclared() { this.declared = true; }

	public void setCSize(int cSize) { this.cSize = cSize;}

	public boolean isDeclared(){ return declared; }

	/*
	 * En las colecciones, el cSize (tamaño de colección) debe definirse previo al tipo
	 */
	public void setVariableType(String type){
		variableType = type;
		if (type == INT)
			cSizeBytes = cSize * 2;
<<<<<<< HEAD
		else //sino es long
=======
		else
>>>>>>> 0fcca1b... varios
			cSizeBytes = cSize * 4;
	}

	public void setIdentifierClass(String identifierClass) { this.identifierClass = identifierClass; }

	public String getIdentifierClass() { return identifierClass; }

	public int getCSizeBytes() { return cSizeBytes; }

	public void setId() { idCadena = idCounter; idCounter++; }

	// Para obtener id único para cadenas
<<<<<<< HEAD
/*	public static int getNewId() {
		idCounter++;
		return idCounter-1;
<<<<<<< HEAD
	}*/
	
=======
	}

>>>>>>> bbb6d5a... commit para pull
	public int getId() { return id; }
=======
	public static int getNewId() { idCounter++; return idCounter-1; }

<<<<<<< HEAD
	public int getId() { return id_cadena; }
>>>>>>> 0fcca1b... varios
=======
	public int getId() { return idCadena; }
>>>>>>> fde7cdb... varios
}