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
			// TODO Auto-generated constructor stub
			super.setHijoIzq(nodo);
		}
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeConver.java

	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,Hashtable<String, ElementoTS> symbolTable) {
		//transforma un int en long
		String dato = super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm,symbolTable);
		if((dato != "AX")&&(dato != "BX")&&(dato != "CX")&&(dato != "DX")) {
			String regDato = registros.getRegFreeInt();
			comAssembler.addMsg("MOV " + regDato + ", " + dato);
		}
		String regAux = registros.getRegFreeLong();
		comAssembler.addMsg("MOV " + regAux + ", " + super.getElem());
		comAssembler.addMsg("CWD");
		return regAux;
	}
}

