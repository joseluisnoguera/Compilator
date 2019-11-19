package utils.syntacticTree;

import java.util.Hashtable;


import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeCommon extends SyntacticTree {

	public SyntacticTreeCommon(String lexeme, SyntacticTree nodoIzq, SyntacticTree nodoDer) {
		super(lexeme);
		super.setHijoIzq(nodoIzq);
		super.setHijoDer(nodoDer);
	}

<<<<<<< HEAD
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
			Hashtable<String, ElementoTS> symbolTable) {
		String datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable);
		String datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable);
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		String datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
		String datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable, deep+1);
>>>>>>> 7eacf2b... _

		Pattern patron = Pattern.compile("_*");
		Matcher matchIzq = patron.matcher(datoIzq);
		Matcher matchDer = patron.matcher(datoDer);

		String op="";

		switch(super.getElem())
		{
		case "+":
			op="ADD";
		case "-":
			op="SUB";
		case "*":
			op="IMUL";
		case "/":
		{
<<<<<<< HEAD
			op="DIV";
<<<<<<< HEAD:src/utils/sintacticTree/SintacticTreeCommon.java

			contEtiquetas++;
=======
=======
			op="IDIV";
>>>>>>> 9acbfaf... comentario
			comAssembler.addMsg("CMP " + datoDer + ", " + 0);
			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0

			/*contEtiquetas++;

					comAssembler.addMsg("CMP " + datoDer + ", " + 0);
					comAssembler.addMsg("JZ _label" + contEtiquetas);//Salto al error del programa si el divisor es 0
				/*	comAssembler.addMsg("_label" + contEtiquetas + ":");
					comAssembler.addMsg("invoke StdOut, addr _Errorlabel" + contEtiquetas);
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeCommon.java

			comAssembler.addMsg("ADD datoDer, "+0);
			comAssembler.addMsg("JZ _label"+contEtiquetas);//Salto al error del programa si el divisor es 0
			comAssembler.addMsg("_label"+contEtiquetas+":");
			comAssembler.addMsg("invoke StdOut, addr _Errorlabel"+contEtiquetas);

			ElementoTS tupla = new ElementoTS("_Errorlabel"+contEtiquetas, "", "Error: division por cero");
			symbolTable.put("_Errorlabel"+contEtiquetas, tupla);

			contEtiquetas++;

			comAssembler.addMsg("JMP _label"+contEtiquetas);//salto al final del programa
			comAssembler.addMsg("_label"+contEtiquetas+":");

			comAssembler.addMsg("invoke ExitProcess, "+0);
		}
		case ":=":
		{
			op="MOV";
		}
		default://si los dos son hojas -- en cualquier otro caso
		{
			op="CMP";
		}
		}
<<<<<<< HEAD
<<<<<<< HEAD:src/utils/sintacticTree/SintacticTreeCommon.java
		if( matchIzq.matches() && matchDer.matches()) 

		{

			//si los dos son hojas
			int reg=registros.getRegFree();//obtener algun registro libre
			boolean state=true;
			registros.setRegTable(reg, state);
			comAssembler.addMsg("MOV R"+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
			comAssembler.addMsg(op+" R"+reg+", "+datoDer);
=======
		if(esHoja(datoIzq) && esHoja(datoDer)){//si los dos son hojas
//			int reg=registros.getRegFree();//obtener algun registro libre
//			boolean state=true;
//			registros.setRegTable(reg, state);
//			comAssembler.addMsg("MOV R"+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
//			comAssembler.addMsg(op+" R"+reg+", "+datoDer);
>>>>>>> cf97fd0... Arreglos en ventana:src/utils/syntacticTree/SyntacticTreeCommon.java
			//devuelve lo que se devuelve por pantalla
			//comInterm.addMsg();
//			return "R"+reg;
		}
		else
			if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/")			


				if(matchIzq.matches())//si el izquierdo es hoja;
					if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()==":=")//es operacion conmutativa
=======
		if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/") {
			if(esHoja(datoIzq) && esHoja(datoDer)){//si los dos son hojas
				if((op == "IMUL")) {//si es operacion de multiplicacion
					String reg;
					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int") {
						comAssembler.addMsg("MOV AX, "+datoIzq);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL AX, "+datoDer);
						reg = registros.getRegFreeLong();
						comAssembler.addMsg("MOV " + reg + ", DX:AX");
						return reg;
					}else {
						comAssembler.addMsg("MOV EAX, "+datoIzq);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL EAX, "+datoDer);
						reg = registros.getRegFreeLong();
//						comAssembler.addMsg(/*comando para reducir a EAX el contenido de EDX:EAX*/); //Línea comentada para poder compilar
						comAssembler.addMsg("MOV " + reg + ", EAX");
						return reg;
					}
				}else { //si no es operacion de multiplicacion
					String reg;
					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int")
						reg = registros.getRegFreeInt();//obtener algun registro int libre
					else
						reg = registros.getRegFreeLong();//obtener algun registro long libre

					comAssembler.addMsg("MOV "+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
					comAssembler.addMsg(op+" "+reg+", "+datoDer);
					return reg;
				}
			}
			else
				if(esHoja(datoIzq))//si el izquierdo es hoja;
					if(op == "ADD" || op == "MOV")//es operacion conmutativa
>>>>>>> 9acbfaf... comentario
					{
						comAssembler.addMsg(op+" "+datoDer+", "+datoIzq);//operacion sobre el mismo registro
						return datoDer;
					}
					else
					{if (op == "IMUL") {

					}else {

					}	
					//						int reg=registros.getRegFree();//obtener algun registro libre
					//						boolean state=true;
					//						registros.setRegTable(reg, state);
					//						comAssembler.addMsg("MOV R"+reg+", "+datoDer);							//devuelvo codigo assembler correspondiente
					//						comAssembler.addMsg(op+" "+reg+", "+datoIzq);
					//comInterm.addMsg();
					//registros.setRegTable(datoIzq, false); datoIzq es un String de la forma R1 , hay que transformarlo a numero no mas
					//						return "R"+reg;
					}
				else
				{
					if(matchDer.matches())
					{	//si el derecho es hoja;
						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
						//comInterm.addMsg();
					}
					else//ninguno es hoja
					{
						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
						//registros.setRegTable(datoDer, false);datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
						//comInterm.addMsg();
					}
					return datoIzq;
				}
		}
		else 
		{
			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!="))
				comAssembler.addMsg("CMP " + datoIzq +", " + datoDer);

<<<<<<< HEAD
			else 
			{
				if(matchDer.matches())
				{
					comAssembler.addMsg("");
				}

				return "";//_S, IF, Cuerpo, comparadores, 



			}
		return datoDer;
	}
=======
			return super.getElem();//_S, IF, Cuerpo, comparadores, 
		}
		return datoDer;
	}

	public boolean esHoja(String dato) {
		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9')&&(dato.charAt(0) >= '0')));
	}
>>>>>>> 9acbfaf... comentario
}
