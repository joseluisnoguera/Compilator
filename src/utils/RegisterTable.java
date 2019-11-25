package utils;


import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Hashtable;

>>>>>>> 39b95a0... Update RegisterTable-VarAUX
import utils.syntacticTree.SyntacticTree;

public class RegisterTable {

<<<<<<< HEAD
	private class Registro{
		public String nombre;
<<<<<<< HEAD
		public Boolean ocupado = false;
		public SyntacticTree nodo;
	}
<<<<<<< HEAD
	private ArrayList< Registro > regState; // False=libre | True=ocupado

	public RegisterTable(int size) {
		this.regState=new ArrayList< Registro >(size);
		for(int i=0; i<this.regState.size();i++)  
		{
=======
=======
=======
	private class Registro {
		public String name;
>>>>>>> 313c55b... extensiones a 32 bits
		public Boolean is_busy; // False: libre, True: ocupado
		public SyntacticTree nodoAsociado;

		public Registro() { is_busy = false; }
		public String toString() { return "Nombre: " + name + " - Estado: " + is_busy + " - Nodo: " + nodoAsociado; }
	}
<<<<<<< HEAD
	
<<<<<<< HEAD
>>>>>>> 88b2c34... _
	private ArrayList<Registro> regState; 
	
	private static final int CANT_REGISTROS = 4; //Cantidad de registros en procesador
	private static int varAUX = 0;
=======
=======

>>>>>>> bbb6d5a... commit para pull
	private static final int CANT_REGISTROS = 4; //Cantidad de registros en procesador
>>>>>>> 51f241d... arreglos varios
	public static final int AX = 0;
	public static final String NAME_AX = "ax";
	public static final int BX = 1;
	public static final String NAME_BX = "bx";
	public static final int CX = 2;
	public static final String NAME_CX = "cx";
	public static final int DX = 3;
	public static final String NAME_DX = "dx";
	public static final int EAX = 0;
	public static final String NAME_EAX = "eax";
	public static final int EBX = 1;
	public static final String NAME_EBX = "ebx";
	public static final int ECX = 2;
	public static final String NAME_ECX = "ecx";
	public static final int EDX = 3;
	public static final String NAME_EDX = "edx";

	private int contadorVarAUX;
	private ArrayList<Registro> regState;

	public RegisterTable() {
<<<<<<< HEAD
<<<<<<< HEAD
		regState = new ArrayList<Registro>(CANT_REGISTROS);
		for(int i = 0; i < regState.size(); i++) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			Registro reg = new Registro();
			this.regState.set(i, reg);
=======
=======
		contadorVarAUX = 0;
>>>>>>> 51f241d... arreglos varios
		regState = new ArrayList<Registro>();
		for(int i = 0; i < CANT_REGISTROS; i++) {
			Registro reg = new Registro();
			regState.add(reg);
>>>>>>> 88b2c34... _
		}
	}

<<<<<<< HEAD
<<<<<<< HEAD
	public String getRegFreeInt(SyntacticTree nodo) {
		Registro reg = null;
		for(int i=0; i<this.regState.size();i++){
=======
	public String getRegFreeInt(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack comAssembler) {
=======
	public String getRegFreeInt(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack assemblerCode) {
>>>>>>> 0fcca1b... varios
		Registro reg = null;
<<<<<<< HEAD
		Boolean encontrado=false;
		for(int i=0; i < this.regState.size(); i++) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
		boolean encontrado = false;
		int i = 0;
		while (i < regState.size() && !encontrado) {
>>>>>>> 51f241d... arreglos varios
			reg = regState.get(i);
<<<<<<< HEAD
			if(!reg.ocupado) {
				switch(i){
					case 0: reg.nombre="AX";
					case 1: reg.nombre="BX";
					case 2: reg.nombre="CX";
					case 3: reg.nombre="DX";
				}
<<<<<<< HEAD
				reg.nodo = nodo;
				reg.ocupado = true;
				return reg.nombre;
			}
		}
		//busca una variable auxiliar
	}

	public String getRegFreeLong(SyntacticTree nodo) {
		Registro reg = null;
		for(int i=0; i<this.regState.size();i++)  
		{
=======
=======
			if(!reg.is_busy) {
<<<<<<< HEAD
				if(i == AX) reg.nombre = NAME_AX;
				else if(i == BX) reg.nombre = NAME_BX;
				else if(i == CX) reg.nombre = NAME_CX;
				else reg.nombre = NAME_DX;
>>>>>>> 507ee97... comentario
=======
				if(i == AX) reg.name = NAME_AX;
				else if(i == BX) reg.name = NAME_BX;
				else if(i == CX) reg.name = NAME_CX;
				else reg.name = NAME_DX;
>>>>>>> 313c55b... extensiones a 32 bits
				reg.nodoAsociado = nodoAsociado;
				reg.is_busy = true;
				encontrado=true;
			}
			i++;
		}
<<<<<<< HEAD
		//TODO: codear logica para asignar variable auxiliar
		if(!encontrado)
		{
			varAUX++;
			String var="_varAux"+varAUX;
			ElementoTS tupla=new ElementoTS(var,"","varAuxiliar");//agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(var,tupla);
			reg=regState.get(CX);
			reg.nodoAsociado.setAlmacenamiento(var);//setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov "+var+", "+reg.nombre);//mueve lo que contiene ECX a var
			reg.nodoAsociado=nodoAsociado;
			reg.nombre=NAME_CX;
		
=======
		if(!encontrado) {
			String nombreVarAux = "_@varAux" + contadorVarAUX++;
			int regPos = (int)((Math.random()*2 + 1)); //devueve como valor 1 o 2
			reg = regState.get(regPos); // Siempre se le asigna a BX o CX
			ElementoTS nuevoElemento = new ElementoTS(ElementoTS.ID, "", reg.nodoAsociado.getType()); //agrego la variable auxiliar a la tabla de simbolos
			nuevoElemento.setIdentifierClass(ElementoTS.VAR);
			symbolTable.put(nombreVarAux.substring(1), nuevoElemento);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux); // setea el nodo del registro almacenado en var
			assemblerCode.addMsg("mov " + nombreVarAux + ", " + reg.name);// mueve lo que contiene CX a var
			reg.nodoAsociado = nodoAsociado;
<<<<<<< HEAD
			reg.nombre = NAME_CX;
>>>>>>> 88b2c34... _
=======
			if (regPos == BX)
				reg.name = NAME_BX;
			if (regPos == CX)
<<<<<<< HEAD
				reg.nombre = NAME_CX;
>>>>>>> 51f241d... arreglos varios
=======
				reg.name = NAME_CX;
>>>>>>> 313c55b... extensiones a 32 bits
		}
		return reg.name;

	}

	public String getRegFreeLong(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack assemblerCode) {
		Registro reg = null;
<<<<<<< HEAD
		Boolean encontrado=false;
		for(int i=0; i<this.regState.size() && !encontrado ;i++) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			reg = regState.get(i);
			if(!(reg.ocupado)) {
				switch(i)
				{
				case 0: reg.nombre="EAX";
				case 1: reg.nombre="EBX";
				case 2: reg.nombre="ECX";
				case 3: reg.nombre="EDX";
=======
		boolean encontrado = false;
		int i = 0;
		while (i < regState.size() && !encontrado) {
			reg = regState.get(i);
			if(!reg.is_busy) {
<<<<<<< HEAD
<<<<<<< HEAD
				switch(i){
				case EAX: reg.nombre = NAME_EAX;
				case EBX: reg.nombre = NAME_EBX;
				case ECX: reg.nombre = NAME_ECX;
				case EDX: reg.nombre = NAME_EDX;
>>>>>>> 51f241d... arreglos varios
				}
<<<<<<< HEAD
				reg.ocupado = true;
				reg.nodo = nodo;
				return reg.nombre;
			}
		}
		//codear logica para asignar variable auxiliar
=======
=======
				if(i == EAX) reg.nombre = NAME_EAX;
				else if(i == EBX) reg.nombre = NAME_EBX;
				else if(i == ECX) reg.nombre = NAME_ECX;
				else reg.nombre = NAME_EDX;
>>>>>>> 507ee97... comentario
=======
				if(i == EAX) reg.name = NAME_EAX;
				else if(i == EBX) reg.name = NAME_EBX;
				else if(i == ECX) reg.name = NAME_ECX;
				else reg.name = NAME_EDX;
>>>>>>> 313c55b... extensiones a 32 bits
				reg.nodoAsociado = nodoAsociado;
				reg.is_busy = true;
				encontrado=true;
			}
			i++;
		}
<<<<<<< HEAD
		//TODO: codear logica para asignar variable auxiliar
		if(!encontrado)
		{
			varAUX++;
			String var="_varAux"+varAUX;
			ElementoTS tupla=new ElementoTS(var,"","varAuxiliar");//agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(var,tupla);
			reg=regState.get(ECX);
			reg.nodoAsociado.setAlmacenamiento(var);//setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov "+var+", ECX");//mueve lo que contiene ECX a var
			reg.nodoAsociado=nodoAsociado;
			reg.nombre=NAME_ECX;
=======
		if(!encontrado) {
			String nombreVarAux = "_@varAux" + contadorVarAUX++;
			int regPos = (int)((Math.random()*2 + 1)); //devuelve como valor 1 o 2
			reg = regState.get(regPos); // Asigna siempre EBX o ECX 
			ElementoTS nuevoElemento = new ElementoTS(ElementoTS.ID, "", reg.nodoAsociado.getType()); //agrego la variable auxiliar a la tabla de simbolos
			nuevoElemento.setIdentifierClass(ElementoTS.VAR);
			symbolTable.put(nombreVarAux.substring(1), nuevoElemento);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux);//setea el nodo del registro almacenado en var
			assemblerCode.addMsg("mov " + nombreVarAux + ", " + reg.name);//mueve lo que contiene ECX a var
			reg.nodoAsociado = nodoAsociado;
<<<<<<< HEAD
			reg.nombre = NAME_ECX;
>>>>>>> 88b2c34... _
=======
			if (regPos == EBX)
				reg.name = NAME_EBX;
			if (regPos == ECX)
<<<<<<< HEAD
				reg.nombre = NAME_ECX;
<<<<<<< HEAD
>>>>>>> 51f241d... arreglos varios
		
		}
		return reg.nombre;
		
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
=======
				reg.name = NAME_ECX;
>>>>>>> 313c55b... extensiones a 32 bits

		}
		return reg.name;

>>>>>>> bbb6d5a... commit para pull
	}

<<<<<<< HEAD
	public void setRegTable(String reg, boolean state) {
		int index=0;
		if(reg == "EBX" || reg=="BX")
			index=1;
		else
			if(reg == "ECX" || reg=="CX")
				index=2;
			else
				if(dato == "EDX" || dato == "DX")
					index=3;
		this.regState.get(index).ocupado = state;
	}
<<<<<<< HEAD
	
<<<<<<< HEAD
	public String getReg(String dato, SyntacticTree nodo, MsgStack comAssembler) {
=======
=======

>>>>>>> bbb6d5a... commit para pull
	public int getRegPos(String nameReg) {
		if(nameReg == NAME_AX || nameReg == NAME_EAX) return AX;
		else if(nameReg == NAME_BX || nameReg == NAME_EBX) return BX;
		else if(nameReg == NAME_CX || nameReg == NAME_ECX) return CX;
		else return DX;
	}

	// Para pedir un registro en específico
	public String getReg(String nameReg, SyntacticTree nodo, MsgStack comAssembler,Hashtable<String, ElementoTS> symbolTable) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
	public void freeReg(int reg) { regState.get(reg).is_busy = false; }
	
	public String extendTo32bits(int reg) { (regState.get(reg)).name = getNameRegister32b(reg); return regState.get(reg).name; }
	
	private String getNameRegister32b(int reg) {
		return (reg == 0)? NAME_EAX :
				(reg == 1)? NAME_EBX :
				(reg == 2)? NAME_ECX : NAME_EDX;
	}

	public int getRegPos(String regName) {
		return  (regName == NAME_AX || regName == NAME_EAX)? AX :
			(regName == NAME_BX || regName == NAME_EBX)? BX :
				(regName == NAME_CX || regName == NAME_ECX)? CX : DX;
	}

	// Para pedir un registro en específico
	public String getReg(String nameReg, SyntacticTree nodo, Hashtable<String, ElementoTS> symbolTable, MsgStack assemblerCode) {
>>>>>>> 0fcca1b... varios
		//chequeo si esta libre, sino llama al cambiaRegistro() del nodo al que apunta el registro y agrega comando de cambio de registro
		int index=0;
		if(dato == "EBX" || dato=="BX")
			index=1;
		else
			if(dato == "ECX" || dato=="CX")
				index=2;
			else
				if(dato == "EDX" || dato == "DX")
					index=3;
		Registro reg = regState.get(index);
		if(reg.ocupado) {
			String regNuevo;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
			if(reg.nombre.length() == 2) {
				regNuevo = getRegFreeInt(reg.nodo);
				reg.nodo.cambiaRegistro(regNuevo);
			}else {
				regNuevo = getRegFreeLong(reg.nodo);
				reg.nodo.cambiaRegistro(regNuevo);
=======
			if(reg.nombre == NAME_AX || reg.nombre == NAME_BX || reg.nombre == NAME_CX || reg.nombre == NAME_DX) {
				regNuevo = getRegFreeInt(reg.nodoAsociado,symbolTable,comAssembler); //TODO: Asumiendo que hay otro registro activo, sino debe mover a var auxiliar
				reg.nodoAsociado.cambiaRegistro(regNuevo);
			} else {
				regNuevo = getRegFreeLong(reg.nodoAsociado,symbolTable,comAssembler);
				reg.nodoAsociado.cambiaRegistro(regNuevo);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			}
			comAssembler.addMsg("MOV " + regNuevo + ", " + dato);
			reg.nodo = nodo;
			reg.nombre = dato;
		}else {
			reg.ocupado = true;
			reg.nodo = nodo;
			reg.nombre = dato;
=======
			if(reg.nombre == NAME_AX || reg.nombre == NAME_BX || reg.nombre == NAME_CX || reg.nombre == NAME_DX)
=======
			if(reg.nodoAsociado.getType() == ElementoTS.INT)
>>>>>>> fde7cdb... varios
				regNuevo = getRegFreeInt(reg.nodoAsociado, symbolTable, assemblerCode);
			else
				regNuevo = getRegFreeLong(reg.nodoAsociado, symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regNuevo + ", " + reg.name);
			reg.nodoAsociado.setAlmacenamiento(regNuevo);
			reg.nodoAsociado = nodo;
			reg.name = nameReg;
		} else {
			reg.is_busy = true;
			reg.nodoAsociado = nodo;
<<<<<<< HEAD
			reg.nombre = nameReg;
>>>>>>> 0fcca1b... varios
=======
			reg.name = nameReg;
>>>>>>> 313c55b... extensiones a 32 bits
		}
		return dato;
	}
}

