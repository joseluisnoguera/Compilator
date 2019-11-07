package utils;

public class SintacticTreeConver extends SintacticTree{


		public SintacticTreeConver(String lexeme, SintacticTree nodo) {
			super(lexeme);
			// TODO Auto-generated constructor stub
			super.setHijoIzq(nodo);
		}

		@Override
		public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm) {
			//transforma un int en long
			String dato = super.getHijoIzq().recorreArbol(registros,comAssembler,comInterm);
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

