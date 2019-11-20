package utils;


import java.util.ArrayList;
import utils.syntacticTree.SyntacticTree;

public class RegisterTable {

	private class Registro{
		public String nombre;
		public Boolean ocupado = false;
		public SyntacticTree nodo;
	}
	private ArrayList< Registro > regState; // False=libre | True=ocupado

	public RegisterTable(int size) {
		this.regState=new ArrayList< Registro >(size);
		for(int i=0; i<this.regState.size();i++)  
		{
			Registro reg = new Registro();
			this.regState.set(i, reg);
		}
	}

	public String getRegFreeInt(SyntacticTree nodo) {
		Registro reg = null;
		for(int i=0; i<this.regState.size();i++){
			reg = regState.get(i);
			if(!reg.ocupado) {
				switch(i){
					case 0: reg.nombre="AX";
					case 1: reg.nombre="BX";
					case 2: reg.nombre="CX";
					case 3: reg.nombre="DX";
				}
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
			reg = regState.get(i);
			if(!(reg.ocupado)) {
				switch(i)
				{
				case 0: reg.nombre="EAX";
				case 1: reg.nombre="EBX";
				case 2: reg.nombre="ECX";
				case 3: reg.nombre="EDX";
				}
				reg.ocupado = true;
				reg.nodo = nodo;
				return reg.nombre;
			}
		}
		//codear logica para asignar variable auxiliar
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
	
	public String getReg(String dato, SyntacticTree nodo, MsgStack comAssembler) {
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
			if(reg.nombre.length() == 2) {
				regNuevo = getRegFreeInt(reg.nodo);
				reg.nodo.cambiaRegistro(regNuevo);
			}else {
				regNuevo = getRegFreeLong(reg.nodo);
				reg.nodo.cambiaRegistro(regNuevo);
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
}

