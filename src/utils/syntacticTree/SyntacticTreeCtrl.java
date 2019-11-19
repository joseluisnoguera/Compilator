package utils.syntacticTree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeCtrl extends SyntacticTree {

	protected static List<String> etiquetas = new ArrayList<String>();

	public SyntacticTreeCtrl(String lexeme, SyntacticTree nodo) {
		super(lexeme);
		
		super.setHijoIzq(nodo);
	}

	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
			Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		if(super.getElem() == "cond") {//cond del foreach
			contEtiquetas++;
			comAssembler.addMsg("_label" + contEtiquetas + ":");
			etiquetas.add("_label" + contEtiquetas);//etiqueta de inicio de condicion de foreach
		}
		String op = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
		switch(super.getElem()){
		case "Cond":{//condicion de if
			contEtiquetas++;
			switch(op) {//se agrega el salto por falso segun la instruccion hacia el final del if (y se agrega su etiqueta)

			case "<":{
				comAssembler.addMsg("JGE _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			case ">":{
				comAssembler.addMsg("JLE _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			case "LET":{
				comAssembler.addMsg("JG _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			case "GET":{
				comAssembler.addMsg("JL _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			case "DIF":{
				comAssembler.addMsg("JE _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			case "EQ":{
				comAssembler.addMsg("JNE _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}

			}
		}

		case "cond": {//cond del foreach
			contEtiquetas++;
			comAssembler.addMsg("JGE _label" + contEtiquetas);//salto por false en caso de que se termine el arreglo
			etiquetas.add("_label" + contEtiquetas);//etiqueta de fin de foreach
		}

		case "Then":{//then que no tendra else
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}

		case "then":{//then que tendra else
			contEtiquetas++;
			comAssembler.addMsg("JMP _label" + contEtiquetas);//se agrega el salto al fin del if (y se agrega despues su etiqueta)
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega etiqueta de inicio de else
			etiquetas.remove(etiquetas.size()-1);
			etiquetas.add("_label" + contEtiquetas);
		}

		case "else":{
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}

		case "CuerpoAvance":{
			comAssembler.addMsg("JMP " + etiquetas.get(etiquetas.size()-2));//salto al principio de la condicion de foreach
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//creacion de etiqueta de fin de foreach
			etiquetas.remove(etiquetas.size()-1);
			etiquetas.remove(etiquetas.size()-1);
		}
		}
		return "";
	}	
}
