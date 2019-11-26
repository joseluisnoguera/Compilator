package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class ST_LeafCollection extends ST_Leaf {
	public ST_LeafCollection(String lexema) {
		super(lexema);
	}

	@Override
	public void recorreArbol(RegisterTable registers, MsgStack assemblerCode, MsgStack comInterm, Hashtable<String,
			ElementoTS> symbolTable, String blankPrefix) { // Se recibe el valor del índice a través del registro del hijo
		String data = getElem();
		System.out.println("en leaf collection: " + data);
		comInterm.addMsg(blankPrefix + "Nodo: " + data);
		if(data.charAt(data.length() - 1) == ']' ) { // Si es una colección
			int subIndexNameStart = 0;
			while (data.charAt(subIndexNameStart) != '[')
				subIndexNameStart++;
			subIndexNameStart++;
			String regIndex = extractValuePointed(data.substring(subIndexNameStart, data.length() - 1), registers, assemblerCode, symbolTable);
			if (getType().equals(ElementoTS.INT)) { // Arr[i] con i perteneciente al índice de foreach (ya extraído su valor dentro del foreach)
				assemblerCode.addMsg("cwde");
				regIndex = registers.extendTo32b(registers.getRegPos(RegisterTable.NAME_AX));
			}
			String regSizeByType = registers.getRegFreeLong(null, symbolTable, assemblerCode);
			int size;
			String collectionName = data.substring(1, subIndexNameStart);
			if(symbolTable.get(collectionName).getVariableType().equals(ElementoTS.INT))
				size = 2;
			else
				size = 4;
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
			if (collectionInLeftSideAssig) { // Si es para el lado izquierdo se devuelve el puntero
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
			registers.freeReg(registers.getRegPos(regEndCollection));
		} else // Caso de _i puntero del lado derecho, convertion ya extrajo valor, pasa directo al nivel superior del árbol
			data = extractValuePointed("", registers, assemblerCode, symbolTable);
		setAlmacenamiento(data);
	}
	
	private String extractValuePointed(String varName, RegisterTable registers, MsgStack assemblerCode, Hashtable<String,
			ElementoTS> symbolTable) {
		String reg;
		String data = (varName == "")? getElem() : varName;
		String regPosicion = registers.getReg(RegisterTable.NAME_EDX, this, symbolTable, assemblerCode);
		assemblerCode.addMsg("mov " + regPosicion + ", " + data);
		if(getType() == ElementoTS.INT) {
			reg = registers.getReg(RegisterTable.NAME_AX, this, symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + reg + ", word ptr [" + regPosicion + "]");
			setType(ElementoTS.INT);
			System.out.println("extrae entero del indice: " + data);
		} else {
			reg = registers.getReg(RegisterTable.NAME_EAX, this, symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + reg + ", dword ptr [" + regPosicion + "]");
			setType(ElementoTS.LONG);
			System.out.println("extrae long del indice: " + data);
		}
		registers.freeReg(registers.getRegPos(regPosicion));
		return reg;
	}
}