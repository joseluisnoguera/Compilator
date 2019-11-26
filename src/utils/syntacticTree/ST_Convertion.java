

package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class ST_Convertion extends SyntacticTree{


<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeConver.java
<<<<<<< HEAD:src/utils/sintacticTree/SintacticTreeConver.java
	public SintacticTreeConver(String lexeme, SintacticTree nodo) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodo);
	}
=======
		public SyntacticTreeConver(String lexeme, SyntacticTree nodo) {
			super(lexeme);
			
			super.setHijoIzq(nodo);
		}
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeConver.java
=======
	public ST_Convertion(String lexeme, SyntacticTree nodo) {
		super(lexeme);
		super.setHijoIzq(nodo);
	}
	
	public static final String ITOL = "itol"; 			// Para convertir int en long
	public static final String PTOV = "pointerToVal";	// Para extraer el valor apuntado por una variable puntero
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Convertion.java

<<<<<<< HEAD
	@Override
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeConver.java
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,Hashtable<String, ElementoTS> symbolTable) {
=======
	public void recorreArbol(RegisterTable registros, MsgStack assemblerCode, MsgStack comInterm,
=======
	public void recorreArbol(RegisterTable registers, MsgStack assemblerCode, MsgStack comInterm,
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Convertion.java
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
>>>>>>> 0fcca1b... varios
		//transforma un int en long
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		String dato = super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable);
		if((dato != "AX")&&(dato != "BX")&&(dato != "CX")&&(dato != "DX")) {
			String regDato = registros.getRegFreeInt();
			comAssembler.addMsg("MOV " + regDato + ", " + dato);
=======
		@Override
<<<<<<< HEAD
		public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,Hashtable<String, ElementoTS> symbolTable, int deep) {
			//TODO: Agregar blancos
			comInterm.addMsg("Nodo: " + super.getElem());
			//transforma un int en long
<<<<<<< HEAD
			String dato = super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable, deep+1);
=======
		public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
				Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
			comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
			//transforma un int en long
			super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable, blankPrefix + " ");
			String dato = getHijoIzq().getAlmacenamiento();
>>>>>>> 154a393... comentario
			if((dato != "AX")&&(dato != "BX")&&(dato != "CX")&&(dato != "DX")) {//no es un registro
				if (dato.charAt(0) == '_') {//es id
					String regDato = registros.getReg("AX",this, comAssembler);
					comAssembler.addMsg("MOV " + regDato + ", " + dato);
					comAssembler.addMsg("CWDE");
					String regAux = registros.getRegFreeLong(this);
					comAssembler.addMsg("MOV " + regAux + ", " + regDato);
					registros.setRegTable("AX",false);
					setAlmacenamiento(regAux);
				}else {//es cte
					String regDato = registros.getRegFreeLong(this);
					comAssembler.addMsg("MOV " + regDato + ", " + dato);
					setAlmacenamiento(regDato);
				}
			}else {//es un registro de 16b
				String regAX = registros.getReg("AX",this, comAssembler);
				comAssembler.addMsg("MOV " + regAX + ", " + dato);
=======
		String dato = "";
		if (getHijoIzq() != null) {
			getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable, blankPrefix + "  ");
			dato = getHijoIzq().getAlmacenamiento();
		}
		if((dato != RegisterTable.NAME_AX) && (dato != RegisterTable.NAME_BX) && (dato != RegisterTable.NAME_CX)
				&& (dato != RegisterTable.NAME_DX)) { //no es un registro
			if (dato.charAt(0) == '_') { //es id
				String regDato = registros.getReg(RegisterTable.NAME_AX, this, comAssembler,symbolTable);
				comAssembler.addMsg("MOV " + regDato + ", " + dato);
>>>>>>> 1375c5c... arreglos varios
=======
=======
		System.out.println("entro a nodo conver " + getElem());
>>>>>>> dbec3d7... comentario
		String data = "";
		getHijoIzq().recorreArbol(registers,assemblerCode,comInterm,symbolTable, blankPrefix + getBlankSpace());
		data = getHijoIzq().getAlmacenamiento();
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeConver.java
<<<<<<< HEAD
		if(getHijoIzq().isVariableOrConst()) {
			if (data.charAt(0) == '_') { //es id
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
				String regDato = registros.getReg(RegisterTable.NAME_AX, this, comAssembler);
=======
				String regDato = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
>>>>>>> 88b2c34... _
				comAssembler.addMsg("MOV " + regDato + ", " + data);
>>>>>>> bca257b... resueltos problemas en common
				comAssembler.addMsg("CWDE");
<<<<<<< HEAD
<<<<<<< HEAD
				String regAux = registros.getRegFreeLong(this);
				comAssembler.addMsg("MOV " + regAux + ", " + regAX);
				registros.setRegTable("AX",false);
=======
				String regAux = registros.getRegFreeLong(this,symbolTable,comAssembler);
=======
				String regAux = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
>>>>>>> 88b2c34... _
				comAssembler.addMsg("MOV " + regAux + ", " + regDato);
				registros.freeReg(RegisterTable.AX);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
				setAlmacenamiento(regAux);
<<<<<<< HEAD
			}
>>>>>>> 7eacf2b... _
=======
			String dato = super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable, blankPrefix + " ");
//			if((dato != "AX")&&(dato != "BX")&&(dato != "CX")&&(dato != "DX")) {//no es un registro
//				if (dato.charAt(0) == '_') {//es id
//					String regDato = registros.getRegAX();
//					comAssembler.addMsg("MOV " + regDato + ", " + dato);
//					comAssembler.addMsg("CWDE");
//					String regAux = registros.getRegFreeLong();
//					comAssembler.addMsg("MOV " + regAux + ", " + regDato);
//					registros.setRegTable("AX",false);
//					return regAux;
//				}else {//es cte
//					String regDato = registros.getRegFreeLong();
//					comAssembler.addMsg("MOV " + regDato + ", " + dato);
//					return regDato;
//				}
//			}else {//es un registro de 16b
//				String regAX = registros.getRegAX();
//				comAssembler.addMsg("MOV " + regAX + ", " + dato);
//				comAssembler.addMsg("CWDE");
//				String regAux = registros.getRegFreeLong();
//				comAssembler.addMsg("MOV " + regAux + ", " + regAX);
//				registros.setRegTable("AX",false);
//				return regAux;
//			}
			return "";
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
			}else {//es cte
<<<<<<< HEAD
<<<<<<< HEAD
				String regDato = registros.getRegFreeLong(this);
=======
				String regDato = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
				comAssembler.addMsg("MOV " + regDato + ", " + data);
				setAlmacenamiento(regDato);
=======
				String regData = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
				comAssembler.addMsg("mov " + regData + ", " + data);
				comAssembler.addMsg("cwde");
				String regConverted = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
				comAssembler.addMsg("mov " + regConverted + ", " + regData);
=======
				String regData = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regData + ", " + data);
				assemblerCode.addMsg("cwde");
<<<<<<< HEAD
				String regConverted = registros.getRegFreeLong(getHijoIzq(),symbolTable,assemblerCode);
				assemblerCode.addMsg("mov " + regConverted + ", " + regData);
>>>>>>> 0fcca1b... varios
				registros.freeReg(RegisterTable.AX);
				setAlmacenamiento(regConverted);
=======
				regData = registros.extendTo32bits(registros.getRegPos(RegisterTable.NAME_AX));
				setAlmacenamiento(regData);
>>>>>>> 313c55b... extensiones a 32 bits
			}else { //es cte
				String regConverted = registros.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regConverted + ", " + data);
				setAlmacenamiento(regConverted);
>>>>>>> 51f241d... arreglos varios
			}
		}else {//es un registro de 16b
<<<<<<< HEAD
<<<<<<< HEAD
			String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler);
=======
			String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
<<<<<<< HEAD
>>>>>>> 88b2c34... _
			comAssembler.addMsg("MOV " + regAX + ", " + data);
=======
				String regDato = registros.getRegFreeLong(this,symbolTable,comAssembler);
				comAssembler.addMsg("MOV " + regDato + ", " + dato);
				setAlmacenamiento(regDato);
			}
		}else {//es un registro de 16b
			String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler,symbolTable);
			comAssembler.addMsg("MOV " + regAX + ", " + dato);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			comAssembler.addMsg("CWDE");
			String regAux = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
			comAssembler.addMsg("MOV " + regAux + ", " + regAX);
			registros.freeReg(RegisterTable.AX);
			setAlmacenamiento(regAux);
>>>>>>> bca257b... resueltos problemas en common
=======
			comAssembler.addMsg("mov " + regAX + ", " + data);
			comAssembler.addMsg("cwde");
			String regConverted = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
			comAssembler.addMsg("mov " + regConverted + ", " + regAX);
=======
			String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regAX + ", " + data);
			assemblerCode.addMsg("cwde");
<<<<<<< HEAD
			String regConverted = registros.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
			assemblerCode.addMsg("mov " + regConverted + ", " + regAX);
>>>>>>> 0fcca1b... varios
			registros.freeReg(RegisterTable.AX);
			setAlmacenamiento(regConverted);
>>>>>>> 51f241d... arreglos varios
=======
			regAX = registros.extendTo32bits(registros.getRegPos(RegisterTable.NAME_AX));
			setAlmacenamiento(regAX);
>>>>>>> 313c55b... extensiones a 32 bits
=======
		if(getElem() == "itol") {
=======
		if(getElem() == ITOL) {
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Convertion.java
			if(getHijoIzq().isVariableOrConst()) {
				if (data.charAt(0) == '_') { //es id
					String regData = registers.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
					assemblerCode.addMsg("mov " + regData + ", " + data);
					assemblerCode.addMsg("cwde");
					regData = registers.extendTo32b(registers.getRegPos(RegisterTable.NAME_AX));
					setAlmacenamiento(regData);
				}else { //es cte
					String regConverted = registers.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
					assemblerCode.addMsg("mov " + regConverted + ", " + data);
					setAlmacenamiento(regConverted);
				}
			}else {//es un registro de 16b
				String regAX = registers.getReg(RegisterTable.NAME_AX, getHijoIzq(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + regAX + ", " + data);
				assemblerCode.addMsg("cwde");
				regAX = registers.extendTo32b(registers.getRegPos(RegisterTable.NAME_AX));
				setAlmacenamiento(regAX);
			}
		}
		if(getElem() == PTOV) {// extraccion de valor en memoria apuntado por una variable ("pointToVal")
			String reg;
			data = getHijoIzq().getElem();
			if(symbolTable.get(data.substring(1)).getVariableType() == ElementoTS.INT) {
				reg = registers.getRegFreeInt(getHijoIzq(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + reg + ", word ptr [" + data + "]");
			} else {
				reg = registers.getRegFreeLong(getHijoIzq(), symbolTable, assemblerCode);
				assemblerCode.addMsg("mov " + reg + ", dword ptr [" + data + "]");
			}
			setAlmacenamiento(reg);
>>>>>>> ffe4c4b... comentario
		}
		String regAux = registros.getRegFreeLong();
		comAssembler.addMsg("MOV " + regAux + ", " + super.getElem());
		comAssembler.addMsg("CWD");
		return regAux;
	}
}

