package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class ST_Leaf extends SyntacticTree{
	
	protected boolean forLeftSideInAssignment;

	public ST_Leaf(String lex)
	{
		super(lex);
		forLeftSideInAssignment = false;
	}
	
	public void setLeftSideAssignment() { forLeftSideInAssignment = true; }
	
	public boolean isCollectionPointer() { return forLeftSideInAssignment; }
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeLeaf.java

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		return super.getElem();
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
=======
=======
	
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Leaf.java
	@Override
	public void recorreArbol(RegisterTable registers, MsgStack assemblerCode, MsgStack comInterm, Hashtable<String,
			ElementoTS> symbolTable, String blankPrefix) {
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeLeaf.java
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
>>>>>>> 154a393... comentario
		String dato=super.getElem();
		if(dato.substring(dato.length()-1)=="]" )//obtiene nombre de la coleccion
		{
			int finColec=1;
			for(int i=0;i<dato.length()-1;i++)
			{
				if(dato.substring(i,i)=="[")//fin de la coleccion
				{
					finColec=i-1;
					break;
				}
			}
<<<<<<< HEAD
			
			String index=dato.substring(finColec+1,dato.length()-2);
<<<<<<< HEAD
=======
		
			String index=dato.substring(finColec+2,dato.length()-2);//contiene nombre de subindice
<<<<<<< HEAD
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			String regI=registros.getRegFreeLong();
=======
			String regI=registros.getRegFreeLong(this);
>>>>>>> e149002... Update SyntacticTreeLeaf.java
			String subindice=dato.substring(finColec+2,finColec+2);
			if(subindice=="_")//es id
=======
			String regI=registros.getRegFreeLong(this);
			if(dato.substring(finColec+1,finColec+1)=="_")//es id
>>>>>>> 154a393... comentario
			{
				
				comAssembler.addMsg("mov "+regI+", "+index);//guarda en reg el valor asociado a index
				
			}
<<<<<<< HEAD
<<<<<<< HEAD
			
			String regColec = registros.getRegFreeLong();
=======

			String regColec = registros.getRegFreeLong(this);
>>>>>>> 154a393... comentario
			String colec=dato.substring(0,finColec);
=======

			String regColec = registros.getRegFreeLong(this);//guarda un registro para contener la coleccion
			String colec=dato.substring(0,finColec);//contiene el nombre de la coleccion
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			if(symbolTable.get(colec).getTipoAtributo()=="int")
			{
				comAssembler.addMsg("mul "+regI+", 2");//guardo en regI la direccion de memoria del registro
			}
<<<<<<< HEAD
			else
			{
				comAssembler.addMsg("mul "+regI+", 4");
			
			}
			comAssembler.addMsg("mov "+regColec+", offset "+colec);//guarda inicio de arreglo
			comAssembler.addMsg("add "+regI+", "+regColec);//guarda direc de memoria que se posiciona index
			comAssembler.addMsg("mov "+regColec+", dword ptr ["+regI+"]");//guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
			registros.setRegTable(regI,false);//libera el registro usado
			dato=regColec;
		}
<<<<<<< HEAD
<<<<<<< HEAD
		
=======
		else
		{
			if(symbolTable.get(dato).getAccMemory())//chequea si el id es indice del foreach
			{
				String reg="";
				if(symbolTable.get(dato).getTipoAtributo()=="int")//chequea tipo del indice
				{
					reg=registros.getRegFreeInt(this);
				}
=======
			String index = dato.substring(finColec+2,dato.length()-2);//contiene nombre de subindice
<<<<<<< HEAD
<<<<<<< HEAD
			String regIndex = registros.getRegFreeLong(this);
			comAssembler.addMsg("mov " + regIndex + ", " + index);
			String regCollection = registros.getRegFreeLong(this);//guarda un registro para contener la coleccion
=======
			String regIndex = registros.getRegFreeLong(this,symbolTable,comAssembler);
=======
			String regIndex = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
			comAssembler.addMsg("mov " + regIndex + ", " + index);
			String regCollection = registros.getRegFreeLong(this,symbolTable,comAssembler);//guarda un registro para contener la coleccion
<<<<<<< HEAD
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			String collectionName = dato.substring(0,finColec); //Comienza en 1 para quitar el _ ya que en la TS no lo tiene
=======
			String collectionName = dato.substring(1,finColec); //Comienza en 1 para quitar el _ ya que en la TS no lo tiene
>>>>>>> 050f179... Update -

=======
=======
		System.out.println(getElem());
>>>>>>> d209296... comentario
=======
		System.out.println("en leaf: " + getElem());
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
=======
		System.out.println("en leaf simple: " + getElem());
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Leaf.java
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
		String data = getElem();
		if(data.charAt(data.length() - 1) == ']' ) { // Si es una colección
			int subIndexNameStart = 0;
			while (data.charAt(subIndexNameStart) != '[')
				subIndexNameStart++;
			subIndexNameStart++; // Salta el corchete inicial
			String subIndexName = data.substring(subIndexNameStart, data.length() - 1);
<<<<<<< HEAD
<<<<<<< HEAD
			String regIndex = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
			comAssembler.addMsg("mov " + regIndex + ", " + subIndexName);
			String regCollectionOffset = registros.getRegFreeLong(this, symbolTable, comAssembler);//guarda un registro para contener la coleccion
<<<<<<< HEAD
			String collectionName = data.substring(1, subIndexNameStart - 2);
>>>>>>> 51f241d... arreglos varios
=======
			String collectionName = data.substring(1, subIndexNameStart - 1);
			System.out.println(collectionName);
			System.out.println(subIndexName);
>>>>>>> 2974fd5... arreglos varios
			if(symbolTable.get(collectionName).getTipoAtributo().equals("int"))
				comAssembler.addMsg("mul " + regIndex + ", 2");
=======
			String regIndex = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regIndex + ", " + subIndexName); // Valor de índice en EAX
=======
			String regIndex = "";
			if (getType().equals(ElementoTS.INT) || (symbolTable.containsKey(subIndexName) && !symbolTable.get(subIndexName.substring(1)).isPointer())) { 
				regIndex = registers.getReg(RegisterTable.NAME_AX, this, symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regIndex + ", " + subIndexName); // Valor de índice en EAX
				assemblerCode.addMsg("cwde");
				regIndex = registers.extendTo32b(registers.getRegPos(RegisterTable.NAME_AX));
			} else {
				regIndex = registers.getReg(RegisterTable.NAME_EAX, this, symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regIndex + ", " + subIndexName); // Valor de índice en EAX
<<<<<<< HEAD
>>>>>>> 313c55b... extensiones a 32 bits
=======
			}
>>>>>>> 7cbc045... correcciones de lo anterior
			String collectionName = data.substring(1, subIndexNameStart - 1);
			String regSizeByType = registers.getRegFreeLong(null, symbolTable, assemblerCode);
			int size;
			if(symbolTable.get(collectionName).getVariableType().equals("int"))
<<<<<<< HEAD
				assemblerCode.addMsg("mul " + regIndex + ", 2");
>>>>>>> 0fcca1b... varios
=======
				size = 2;
>>>>>>> fde7cdb... varios
			else
				size = 4;
			System.out.println("en leaf con " + collectionName + "[" + subIndexName + "]");
			assemblerCode.addMsg("mov " + regSizeByType + ", " + size);
			assemblerCode.addMsg("mul " + regSizeByType); // Calcula el índice en Bytes (EDX:EAX = EAX * regSizeByType)
			registers.freeReg(registers.getRegPos(regSizeByType));
			String regCollectionOffset = registers.getRegFreeLong(this, symbolTable, assemblerCode);
			assemblerCode.addMsg("lea " + regCollectionOffset + ", " + "_" + collectionName); // Base de la colección
			assemblerCode.addMsg("add " + regIndex + ", " + regCollectionOffset); // Calcula posición del índice en memoria (Base + Desplazamiento)
			//chequeo por arreglo superado
			String regEndCollection = registers.getRegFreeLong(this,symbolTable,assemblerCode);
			assemblerCode.addMsg("mov " + regEndCollection + ", " + symbolTable.get(collectionName).getCSizeBytes()); //guarda el tamaño en bytes del arreglo
			assemblerCode.addMsg("add " + regEndCollection + ", " + regCollectionOffset); //guarda la direccion final del arreglo
			assemblerCode.addMsg("cmp " + regIndex + ", " + regEndCollection); //compara direccion final del arreglo con direccion a la que se desea acceder del arreglo
			assemblerCode.addMsg("jge _msgArregloFueraDeRango");
			//chequeo por direccion de memoria menor (por subíndices negativos)
			assemblerCode.addMsg("cmp " + regIndex + ", " + regCollectionOffset); //compara direccion final del arreglo con direccion a la que se desea acceder del arreglo
			assemblerCode.addMsg("jl _msgArregloFueraDeRango");
			if (forLeftSideInAssignment) { // Si es para el lado izquierdo se devuelve el puntero
				data = regIndex;
				registers.freeReg(registers.getRegPos(regCollectionOffset));
			} else { // Para el lado derecho se devuelve el valor apuntado
				if (getType().equals(ElementoTS.INT)) {
					registers.freeReg(registers.getRegPos(regCollectionOffset));
					data = registers.getRegFreeInt(this, symbolTable, assemblerCode);
					assemblerCode.addMsg("mov " + data + ", word ptr [" + regIndex + "]"); //guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
				} else {
					assemblerCode.addMsg("mov " + regCollectionOffset + ", dword ptr [" + regIndex + "]");
					data = regCollectionOffset;
				}
				registers.freeReg(registers.getRegPos(regIndex));
			}
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeLeaf.java
			registros.freeReg(registros.getRegPos(regIndex));
			registros.freeReg(registros.getRegPos(regEndCollection));
<<<<<<< HEAD
			data = regCollectionOffset;
<<<<<<< HEAD
			
<<<<<<< HEAD
		} else {
<<<<<<< HEAD
<<<<<<< HEAD
			System.out.println("Lexema completo: " + dato);
			System.out.println("Lexema: " + dato.substring(1));
			System.out.println("En TS: " + symbolTable.get(dato.substring(1)));
			System.out.println("Hash: " + symbolTable.toString());
			if(symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
				String reg = "";
				if(symbolTable.get(dato).getTipoAtributo().equals(ElementoTS.INT)) //chequea tipo del indice
					reg = registros.getRegFreeInt(this);
>>>>>>> 1375c5c... arreglos varios
				else
					reg=registros.getRegFreeLong(this);
				comAssembler.addMsg("mov "+reg+", dword ptr ["+dato+"]");//mueve a reg el dato almacenado en la direccion de memoria guardada en dato.
				dato=reg;
			}
=======
			if (dato.charAt(0) == '_')
				if(symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
=======
			if (dato.charAt(0) == '_' && symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
>>>>>>> 050f179... Update -
=======
		}
		// Si es variable o constante no se hace nada, pasa directo, las colecciones ya se tomaron arriba
		/* else {
			if (data.charAt(0) == '_') {
>>>>>>> 51f241d... arreglos varios
					String reg = "";
					if(symbolTable.get(data).getTipoAtributo().equals("int")) //chequea tipo del indice
						reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
					comAssembler.addMsg("mov " + reg + ", dword ptr [" + data + "]");//mueve a reg el dato almacenado en la direccion de memoria guardada en dato.
					data = reg;
				}
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
		}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
		return dato;
>>>>>>> 3c084f9... Update SyntacticTreeMemory-Leaf
=======
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String,
			ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
//		String dato=super.getElem();
//		if(dato.substring(dato.length()-1)=="]" )
//		{
//			String lexico="";
//			int finColec=1;
//			for(int i=1;i<dato.length()-1;i++)
//			{
//				if(dato.substring(1,i)!="[")
//				{
//					lexico=dato.substring(1, i)+lexico;//contiene el id del arreglo
//					finColec=i;
//				}
//				else
//					break;
//			}
//
//			String index=dato.substring(finColec+1,dato.length()-2);
//			String regI=registros.getRegFreeLong();
//			if(dato.substring(finColec+1,finColec+1)=="_")//es id
//			{
//
//				comAssembler.addMsg("mov "+regI+", "+index);//guarda en reg el valor asociado a index
//
//			}
//
//			String regColec = registros.getRegFreeLong();
//			String colec=dato.substring(0,finColec);
//			if(symbolTable.get(colec).getTipoAtributo()=="int")
//			{
//				comAssembler.addMsg("mul "+regI+", 2");//guardo en regI la direccion de memoria del registro
//			}
//			else
//			{
//				comAssembler.addMsg("mul "+regI+", 4");
//
//			}
//			comAssembler.addMsg("mov "+regColec+", offset "+colec);//guarda inicio de arreglo
//			comAssembler.addMsg("add "+regI+", "+regColec);//guarda direc de memoria que se posiciona index
//			comAssembler.addMsg("mov "+regColec+", dword ptr ["+regI+"]");//guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
//			registros.setRegTable(regI,false);//libera el registro usado
//			registros.setRegTable(regColec,false);//libera el registro usado
//			dato=regColec;
//		}
//		return dato;
		return "";
>>>>>>> 45299ea... visualizaciÃ³n de Ã¡rbol sintÃ¡ctico
=======

		setAlmacenamiento(dato);
>>>>>>> 154a393... comentario
=======
		
		
>>>>>>> e149002... Update SyntacticTreeLeaf.java
=======
		setAlmacenamiento(dato);
>>>>>>> bca257b... resueltos problemas en common
=======
		setAlmacenamiento(dato);
>>>>>>> 050f179... Update -
=======
		}*/
=======
=======
>>>>>>> fde7cdb... varios
=======
			registers.freeReg(registers.getRegPos(regEndCollection));
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Leaf.java
		}
<<<<<<< HEAD
>>>>>>> d209296... comentario
=======
		// Tratamiento de punteros como enteros, devuelve un registro de 16 bits, tira la parte alta
		if (isVariable() && getType().equals(ElementoTS.INT)  && symbolTable.get(getElem().substring(1)).isPointer() && !forLeftSideInAssignment) {
			String regEAX = registers.getReg(RegisterTable.NAME_EAX, this, symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regEAX + ", " + getElem());
			regEAX = registers.reduceTo16b(registers.getRegPos(regEAX)); // Comienza utilizar AX
			String regResult = registers.getRegFreeInt(this, symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regResult + ", " + regEAX);
			registers.freeReg(registers.getRegPos(regEAX));
			data = regResult;
		}
>>>>>>> ff6e773... uso de punteros como enteros
		setAlmacenamiento(data);
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeLeaf.java
>>>>>>> 51f241d... arreglos varios
	}
	

	
=======
	}	
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Leaf.java
}

