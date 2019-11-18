package utils.sintacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SintacticTreeConver extends SintacticTree{


	public SintacticTreeConver(String lexeme, SintacticTree nodo) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodo);
	}

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

