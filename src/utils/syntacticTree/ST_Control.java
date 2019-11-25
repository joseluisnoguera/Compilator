package utils.syntacticTree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class ST_Control extends SyntacticTree {

	private static List<String> etiquetas = new ArrayList<String>();
	private static int contEtiquetas = 0;

	public ST_Control(String lexeme, SyntacticTree nodo) {
		super(lexeme);
		super.setHijoIzq(nodo);
	}

	@Override
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeCtrl.java
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
<<<<<<< HEAD
			Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
=======
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
=======
	public void recorreArbol(RegisterTable registers, MsgStack assemblerCode, MsgStack comInterm,
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Control.java
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
<<<<<<< HEAD
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
>>>>>>> 154a393... comentario
		if(super.getElem() == "cond") {//cond del foreach
=======
		System.out.println("entro a ctrl " + getElem());
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
		if(getElem() == "COND_FOREACH") { //cond del foreach
>>>>>>> d209296... comentario
			contEtiquetas++;
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeCtrl.java
			comAssembler.addMsg("_label" + contEtiquetas + ":");
			etiquetas.add("_label" + contEtiquetas);//etiqueta de inicio de condicion de foreach
		}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		String op = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
=======
		super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");
		String op = getHijoIzq().getAlmacenamiento();
>>>>>>> 154a393... comentario
		switch(super.getElem()){
		case "Cond":{//condicion de if
=======
		String op = "";
		if (getHijoIzq() != null) {
			getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + "  ");
			op = getHijoIzq().getAlmacenamiento();
		}
=======
		getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + "  ");
		String op = getHijoIzq().getAlmacenamiento();
>>>>>>> 88b2c34... _
=======
		getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + getBlankSpace());
<<<<<<< HEAD
		
>>>>>>> 51f241d... arreglos varios
		switch(getElem()){
		case "COND_IF":{ //condicion de if
>>>>>>> 1375c5c... arreglos varios
			contEtiquetas++;
<<<<<<< HEAD
			switch(op) {//se agrega el salto por falso segun la instruccion hacia el final del if (y se agrega su etiqueta)

=======
			switch(getHijoIzq().getAlmacenamiento()) { //se agrega el salto por falso segun la instruccion hacia el final del if (y se agrega su etiqueta)
>>>>>>> 51f241d... arreglos varios
			case "<":{
=======
=======
			assemblerCode.addMsg("_label" + contEtiquetas + ":");
			etiquetas.add("_label" + contEtiquetas); //etiqueta de inicio de condicion de foreach
		}
		getHijoIzq().recorreArbol(registers, assemblerCode, comInterm,symbolTable, blankPrefix + getBlankSpace());
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Control.java
		System.out.println("resuelve a ctrl " + getElem());
		if(getElem() == "COND_IF") {
			contEtiquetas++;
			if(getHijoIzq().getAlmacenamiento() == "<") {
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeCtrl.java
>>>>>>> d209296... comentario
				comAssembler.addMsg("jge _label" + contEtiquetas);
=======
				assemblerCode.addMsg("jge _label" + contEtiquetas);
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Control.java
				etiquetas.add("_label" + contEtiquetas);
			}
			else if(getHijoIzq().getAlmacenamiento() == ">") {
				assemblerCode.addMsg("jle _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			else if(getHijoIzq().getAlmacenamiento() == "LET") {
				assemblerCode.addMsg("jg _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			else if(getHijoIzq().getAlmacenamiento() == "GET") {
				assemblerCode.addMsg("jl _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
			else if(getHijoIzq().getAlmacenamiento() == "EQ") {
				assemblerCode.addMsg("jne _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
<<<<<<< HEAD

			}
		}

		case "cond": {//cond del foreach
=======
			else if(getHijoIzq().getAlmacenamiento() == "DIF") {
				assemblerCode.addMsg("je _label" + contEtiquetas);
				etiquetas.add("_label" + contEtiquetas);
			}
		}
		else if(getElem() == "COND_FOREACH") {
>>>>>>> d209296... comentario
			contEtiquetas++;
			assemblerCode.addMsg("jge _label" + contEtiquetas); //salto en caso de que se termine el arreglo
			etiquetas.add("_label" + contEtiquetas);//etiqueta de fin de foreach
		}
<<<<<<< HEAD

<<<<<<< HEAD
		case "Then":{//then que no tendra else
=======
		case "THEN":{ //then que no tendra else
>>>>>>> bca257b... resueltos problemas en common
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}

<<<<<<< HEAD
		case "then":{//then que tendra else
=======
		case "THEN_ELSE":{ //then que tendra else
>>>>>>> bca257b... resueltos problemas en common
=======
		else if(getElem() == "THEN") {
			assemblerCode.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}
		else if(getElem() == "THEN_ELSE") {
>>>>>>> d209296... comentario
			contEtiquetas++;
			assemblerCode.addMsg("jmp _label" + contEtiquetas);//se agrega el salto al fin del if (y se agrega despues su etiqueta)
			assemblerCode.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega etiqueta de inicio de else
			etiquetas.remove(etiquetas.size()-1);
			etiquetas.add("_label" + contEtiquetas);
		}
<<<<<<< HEAD

		case "else":{
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}

<<<<<<< HEAD
		case "CuerpoAvance":{
			comAssembler.addMsg("JMP " + etiquetas.get(etiquetas.size()-2));//salto al principio de la condicion de foreach
=======
		case "CUERPOAVANCE":{
=======
		else if(getElem() == "ELSE") {
			assemblerCode.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
			etiquetas.remove(etiquetas.size()-1);
		}
		else if(getElem() == "CUERPOAVANCE") {
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeCtrl.java
>>>>>>> d209296... comentario
			comAssembler.addMsg("jmp " + etiquetas.get(etiquetas.size()-2));//salto al principio de la condicion de foreach
>>>>>>> 51f241d... arreglos varios
			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//creacion de etiqueta de fin de foreach
=======
			assemblerCode.addMsg("jmp " + etiquetas.get(etiquetas.size()-2));//salto al principio de la condicion de foreach
			assemblerCode.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//creacion de etiqueta de fin de foreach
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Control.java
			etiquetas.remove(etiquetas.size()-1);
			etiquetas.remove(etiquetas.size()-1);
		}
<<<<<<< HEAD
		}
<<<<<<< HEAD
=======
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
//		if(super.getElem() == "cond") {//cond del foreach
//			contEtiquetas++;
//			comAssembler.addMsg("_label" + contEtiquetas + ":");
//			etiquetas.add("_label" + contEtiquetas);//etiqueta de inicio de condicion de foreach
//		}
		String op;
		if (getHijoIzq() != null)
			op = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");
//		switch(super.getElem()){
//		case "Cond":{//condicion de if
//			contEtiquetas++;
//			switch(op) {//se agrega el salto por falso segun la instruccion hacia el final del if (y se agrega su etiqueta)
//
//			case "<":{
//				comAssembler.addMsg("JGE _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//			case ">":{
//				comAssembler.addMsg("JLE _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//			case "LET":{
//				comAssembler.addMsg("JG _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//			case "GET":{
//				comAssembler.addMsg("JL _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//			case "DIF":{
//				comAssembler.addMsg("JE _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//			case "EQ":{
//				comAssembler.addMsg("JNE _label" + contEtiquetas);
//				etiquetas.add("_label" + contEtiquetas);
//			}
//
//			}
//		}
//
//		case "cond": {//cond del foreach
//			contEtiquetas++;
//			comAssembler.addMsg("JGE _label" + contEtiquetas);//salto por false en caso de que se termine el arreglo
//			etiquetas.add("_label" + contEtiquetas);//etiqueta de fin de foreach
//		}
//
//		case "Then":{//then que no tendra else
//			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
//			etiquetas.remove(etiquetas.size()-1);
//		}
//
//		case "then":{//then que tendra else
//			contEtiquetas++;
//			comAssembler.addMsg("JMP _label" + contEtiquetas);//se agrega el salto al fin del if (y se agrega despues su etiqueta)
//			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega etiqueta de inicio de else
//			etiquetas.remove(etiquetas.size()-1);
//			etiquetas.add("_label" + contEtiquetas);
//		}
//
//		case "else":{
//			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//se agrega la etiqueta de fin de if
//			etiquetas.remove(etiquetas.size()-1);
//		}
//
//		case "CuerpoAvance":{
//			comAssembler.addMsg("JMP " + etiquetas.get(etiquetas.size()-2));//salto al principio de la condicion de foreach
//			comAssembler.addMsg(etiquetas.get(etiquetas.size()-1) + ":");//creacion de etiqueta de fin de foreach
//			etiquetas.remove(etiquetas.size()-1);
//			etiquetas.remove(etiquetas.size()-1);
//		}
//		}
>>>>>>> 45299ea... visualización de árbol sintáctico
		return "";
=======
=======
>>>>>>> d209296... comentario
		setAlmacenamiento("");
>>>>>>> 154a393... comentario
	}	
}
