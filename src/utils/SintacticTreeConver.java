package utils;

import java.util.Hashtable;

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
			if((dato != "AX")&&(dato != "BX")&&(dato != "CX")&&(dato != "DX")) {//no es un registro
				if (dato.charAt(0) == '_') {//es id
					String regDato = registros.getRegAX();
					comAssembler.addMsg("MOV " + regDato + ", " + dato);
					comAssembler.addMsg("CWDE");
					String regAux = registros.getRegFreeLong();
					comAssembler.addMsg("MOV " + regAux + ", " + regDato);
					registros.setRegTable("AX",false);
					return regAux;
				}else {//es cte
					String regDato = registros.getRegFreeLong();
					comAssembler.addMsg("MOV " + regDato + ", " + dato);
					return regDato;
				}
			}else {//es un registro de 16b
				String regAX = registros.getRegAX();
				comAssembler.addMsg("MOV " + regAX + ", " + dato);
				comAssembler.addMsg("CWDE");
				String regAux = registros.getRegFreeLong();
				comAssembler.addMsg("MOV " + regAux + ", " + regAX);
				registros.setRegTable("AX",false);
				return regAux;
			}
		}
}

