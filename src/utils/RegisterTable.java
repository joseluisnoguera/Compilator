package utils;


import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Hashtable;

>>>>>>> 39b95a0... Update RegisterTable-VarAUX
import utils.syntacticTree.SyntacticTree;

public class RegisterTable {

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
		public Boolean is_busy; // False: libre, True: ocupado
		public SyntacticTree nodoAsociado;

		public Registro() { is_busy = false; }
		public String toString() { return "Nombre: " + nombre + " - Estado: " + is_busy + " - Nodo: " + nodoAsociado; }
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
	public static final String NAME_AX = "AX";
	public static final int BX = 1;
	public static final String NAME_BX = "BX";
	public static final int CX = 2;
	public static final String NAME_CX = "CX";
	public static final int DX = 3;
	public static final String NAME_DX = "DX";
	public static final int EAX = 0;
	public static final String NAME_EAX = "EAX";
	public static final int EBX = 1;
	public static final String NAME_EBX = "EBX";
	public static final int ECX = 2;
	public static final String NAME_ECX = "ECX";
	public static final int EDX = 3;
	public static final String NAME_EDX = "EDX";

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
	public String getRegFreeInt(SyntacticTree nodo) {
		Registro reg = null;
		for(int i=0; i<this.regState.size();i++){
=======
	public String getRegFreeInt(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack comAssembler) {
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
				if(i == AX) reg.nombre = NAME_AX;
				else if(i == BX) reg.nombre = NAME_BX;
				else if(i == CX) reg.nombre = NAME_CX;
				else reg.nombre = NAME_DX;
>>>>>>> 507ee97... comentario
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
			ElementoTS tupla = new ElementoTS(ElementoTS.ID, "", ElementoTS.INT); //agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(nombreVarAux, tupla);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux); // setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov " + nombreVarAux + ", " + reg.nombre);// mueve lo que contiene CX a var
			reg.nodoAsociado = nodoAsociado;
<<<<<<< HEAD
			reg.nombre = NAME_CX;
>>>>>>> 88b2c34... _
=======
			if (regPos == BX)
				reg.nombre = NAME_BX;
			if (regPos == CX)
				reg.nombre = NAME_CX;
>>>>>>> 51f241d... arreglos varios
		}
		return reg.nombre;

	}

	public String getRegFreeLong(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack comAssembler) {
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
			ElementoTS tupla = new ElementoTS(ElementoTS.ID, "",  ElementoTS.LONG);//agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(nombreVarAux,tupla);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux);//setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov " + nombreVarAux + ", " + reg.nombre);//mueve lo que contiene ECX a var
			reg.nodoAsociado = nodoAsociado;
<<<<<<< HEAD
			reg.nombre = NAME_ECX;
>>>>>>> 88b2c34... _
=======
			if (regPos == EBX)
				reg.nombre = NAME_EBX;
			if (regPos == ECX)
				reg.nombre = NAME_ECX;
<<<<<<< HEAD
>>>>>>> 51f241d... arreglos varios
		
		}
		return reg.nombre;
		
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======

		}
		return reg.nombre;

>>>>>>> bbb6d5a... commit para pull
	}

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
		}
		return dato;
	}

	public String toString() {
		return regState.toString();
	}
}

