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
	
>>>>>>> 88b2c34... _
	private ArrayList<Registro> regState; 
	
	private static final int CANT_REGISTROS = 4; //Cantidad de registros en procesador
	private static int varAUX = 0;
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
	
	public RegisterTable() {
<<<<<<< HEAD
		regState = new ArrayList<Registro>(CANT_REGISTROS);
		for(int i = 0; i < regState.size(); i++) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			Registro reg = new Registro();
			this.regState.set(i, reg);
=======
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
		Boolean encontrado=false;
		for(int i=0; i < this.regState.size(); i++) {
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			reg = regState.get(i);
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
				reg.nodoAsociado = nodoAsociado;
				reg.is_busy = true;
				encontrado=true;
			}
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
			contadorVarAUX++;
			String nombreVarAux = "_@varAux" + contadorVarAUX;
			reg = regState.get(CX); // Siempre se le asigna a CX
			System.out.println(reg.nodoAsociado.getElem());
			ElementoTS tupla = new ElementoTS(ElementoTS.ID, "", ElementoTS.INT); //agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(nombreVarAux, tupla);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux); // setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov " + nombreVarAux + ", " + reg.nombre);// mueve lo que contiene CX a var
			reg.nodoAsociado = nodoAsociado;
			reg.nombre = NAME_CX;
>>>>>>> 88b2c34... _
		}
		return reg.nombre;
		
	}

	public String getRegFreeLong(SyntacticTree nodoAsociado, Hashtable<String, ElementoTS> symbolTable, MsgStack comAssembler) {
		Registro reg = null;
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
				}
<<<<<<< HEAD
				reg.ocupado = true;
				reg.nodo = nodo;
				return reg.nombre;
			}
		}
		//codear logica para asignar variable auxiliar
=======
				reg.nodoAsociado = nodoAsociado;
				reg.is_busy = true;
				encontrado=true;
			}
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
			contadorVarAUX++;
			String nombreVarAux = "_@varAux" + contadorVarAUX;
			reg = regState.get(ECX); // Asigna siempre ECX 
			ElementoTS tupla = new ElementoTS(ElementoTS.ID, "",  ElementoTS.LONG);//agrego la variable auxiliar a la tabla de simbolos
			symbolTable.put(nombreVarAux,tupla);
			reg.nodoAsociado.setAlmacenamiento(nombreVarAux);//setea el nodo del registro almacenado en var
			comAssembler.addMsg("mov " + nombreVarAux + ", " + reg.nombre);//mueve lo que contiene ECX a var
			reg.nodoAsociado = nodoAsociado;
			reg.nombre = NAME_ECX;
>>>>>>> 88b2c34... _
		
		}
		return reg.nombre;
		
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
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
	public String getReg(String dato, SyntacticTree nodo, MsgStack comAssembler) {
=======
	public int getRegPos(String nameReg) {
		switch (nameReg) {
			case NAME_AX: case NAME_EAX: return AX;
			case NAME_BX: case NAME_EBX: return BX;
			case NAME_CX: case NAME_ECX: return CX;
			case NAME_DX: case NAME_EDX: return DX;
		}
		return -1;
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

