package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeConver extends SyntacticTree{


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

<<<<<<< HEAD
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,Hashtable<String, ElementoTS> symbolTable) {
		//transforma un int en long
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
				comAssembler.addMsg("CWDE");
				String regAux = registros.getRegFreeLong(this);
				comAssembler.addMsg("MOV " + regAux + ", " + regAX);
				registros.setRegTable("AX",false);
				setAlmacenamiento(regAux);
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
		}
		String regAux = registros.getRegFreeLong();
		comAssembler.addMsg("MOV " + regAux + ", " + super.getElem());
		comAssembler.addMsg("CWD");
		return regAux;
	}
}

