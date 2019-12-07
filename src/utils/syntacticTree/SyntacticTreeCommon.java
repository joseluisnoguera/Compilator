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
<<<<<<< HEAD
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
<<<<<<< HEAD
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
=======
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm,
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
<<<<<<< HEAD
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");
		super.getHijoDer().recorreArbol(registros, comAssembler, comInterm,symbolTable, blankPrefix + " ");

		String dato1 = getHijoIzq().getAlmacenamiento();
		String dato2 = getHijoIzq().getAlmacenamiento();
>>>>>>> 154a393... comentario

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
<<<<<<< HEAD
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
=======
			comAssembler.addMsg("CMP " + dato2 + ", " + 0);
			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0
>>>>>>> 154a393... comentario
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
			if(esHoja(dato1) && esHoja(dato2)){//si los dos son hojas
				if((op == "IMUL")) {//si es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(0)).getTipoAtributo() == "int") {
						String regAX = registros.getReg("AX", this, comAssembler);
						String regDX = registros.getReg("DX", this, comAssembler);
						dato1 = getHijoIzq().getAlmacenamiento();
						dato2 = getHijoDer().getAlmacenamiento();
						comAssembler.addMsg("MOV "+ regAX + ", " + dato1);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL "+regAX+", "+dato2);
						reg = registros.getRegFreeLong(this);
						comAssembler.addMsg("MOV " + reg + ", DX:AX");
						registros.setRegTable("AX",false);
						registros.setRegTable("DX",false);
						setAlmacenamiento(reg);
					}else {
						String regEAX = registros.getReg("EAX",this, comAssembler);
						dato1 = getHijoIzq().getAlmacenamiento();
						dato2 = getHijoDer().getAlmacenamiento();
						comAssembler.addMsg("MOV EAX, "+dato1);				//devuelvo codigo assembler correspondiente
						comAssembler.addMsg("IMUL EAX, "+dato2);
						//						comAssembler.addMsg(/*comando para reducir a EAX el contenido de EDX:EAX*/); //L�nea comentada para poder compilar
						setAlmacenamiento("EAX");
					}
				}else { //si no es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(0)).getTipoAtributo() == "int")
						reg = registros.getRegFreeInt(this);//obtener algun registro int libre
=======
		if(getElem() == "+" || getElem() == "*" || getElem()=="-" || getElem()==":=" || getElem()=="/") {
			if(esHoja(dato1) && esHoja(dato2)) {//si los dos son hojas
				if((op == "IMUL")) {//si es operacion de multiplicacion
					String reg;
					if(symbolTable.get(dato1.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
=======
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
		String dataFromLeft = "";
		String dataFromRight = "";
		getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + "  ");
		if (getHijoDer() != null)
			getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + "  ");
		dataFromLeft = getHijoIzq().getAlmacenamiento();
		if (getHijoDer() != null)
			dataFromRight = getHijoDer().getAlmacenamiento();

		///// OBTENER OPERACI�N /////
		String operation = "";
		switch(getElem()){
		case "+": operation = "ADD";
		case "-": operation = "SUB";
		case "*": operation = "IMUL";
		case "/": {
			operation = "IDIV";
			comAssembler.addMsg("CMP " + dataFromRight + ", " + 0);
			comAssembler.addMsg("JZ _DivisionPorCero"); //Salto a la subrutina de programa si el divisor es 0
		}
		case ":=": operation = "MOV";
		default: operation = "CMP"; //si los dos son hojas -- en cualquier otro caso
		}

		///// GENERAR C�DIGO ADECUADO A CADA OPERACI�N /////
		if(getElem() == "+" || getElem() == "*" || getElem() == "-" || getElem() == ":=" || getElem() == "/") {
<<<<<<< HEAD
			if((getHijoIzq() != null) && ((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) && (getHijoDer() != null) && ((((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) {//si los dos son hojas
				if((operation == "IMUL")) {//si es operacion de multiplicacion TODO: Si el elemento de la izquierda es const �no se rompe?
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
=======
			if(((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()) && (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))) { ///// AMBOS SON CONST O VAR /////
<<<<<<< HEAD
				///// OPERACI�N MILTIPLICACI�N /////
				if((operation == "IMUL")) {
					if(isInt(dataFromLeft, symbolTable)) {
>>>>>>> bca257b... resueltos problemas en common
						String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, this, comAssembler); // Se reserva para, si tiene algo, no pisarlo
=======
						String regAX = registros.getReg(RegisterTable.NAME_AX, this, comAssembler,symbolTable);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, this, comAssembler,symbolTable); //Se reserva para, si tiene algo, no pisarlo
						dataFromLeft = getHijoIzq().getAlmacenamiento();
						dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> 050f179... Update -
=======
				if((operation == "IMUL")) { ///// OPERACI�N MULTIPLICACI�N /////
					if(isInt(dataFromLeft, symbolTable)) { ///// ENTERO /////
						String regAX = registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler, symbolTable);
						@SuppressWarnings("unused")
						String regDX = registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler, symbolTable); // Se reserva para, si tiene algo, no pisarlo
>>>>>>> 88b2c34... _
						comAssembler.addMsg("MOV " + regAX + ", " + dataFromLeft);
						comAssembler.addMsg("IMUL " + regAX + ", " + dataFromRight);
						String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
						comAssembler.addMsg("MOV " + reg + ", DX:AX");
						registros.freeReg(RegisterTable.AX);
						registros.freeReg(RegisterTable.DX);
						setAlmacenamiento(reg);
<<<<<<< HEAD
<<<<<<< HEAD
					} else {
						String regEAX = registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX,this, comAssembler); // Nuevamente se reserva para, si tiene algo, no pisarlo
=======
					}else {
						String regEAX = registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable); //Se reserva para, si tiene algo, no pisarlo
						dataFromLeft = getHijoIzq().getAlmacenamiento();
						dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
					} else { ///// LONG /////
						String regEAX = registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler, symbolTable);
						@SuppressWarnings("unused")
						String regEDX = registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler, symbolTable); // Nuevamente se reserva para, si tiene algo, no pisarlo
>>>>>>> 88b2c34... _
						comAssembler.addMsg("MOV " + regEAX + ", " + dataFromLeft);
						comAssembler.addMsg("IMUL " + regEAX + ", " + dataFromRight);
						setAlmacenamiento(RegisterTable.NAME_EAX);
						registros.freeReg(RegisterTable.EDX);
						setAlmacenamiento(regEAX);
					}
				} else { ///// NO ES MULTIPLICACI�N /////
					String reg;
<<<<<<< HEAD
					if(isInt(dataFromLeft, symbolTable))
<<<<<<< HEAD
						reg = registros.getRegFreeInt(this);
>>>>>>> 1375c5c... arreglos varios
					else
<<<<<<< HEAD
						reg = registros.getRegFreeLong(this);//obtener algun registro long libre

					comAssembler.addMsg("MOV "+reg+", "+dato1);				//devuelvo codigo assembler correspondiente
					comAssembler.addMsg(op+" "+reg+", "+dato2);
=======
						reg = registros.getRegFreeLong(this);
=======
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT))
						reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
						reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
					else
						reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler);
>>>>>>> 88b2c34... _
					comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
					comAssembler.addMsg(operation +" " + reg + ", " + dataFromRight);
>>>>>>> a091e6e... arreglos por punteros null
					setAlmacenamiento(reg);
				}
<<<<<<< HEAD
<<<<<<< HEAD
			}
			else
<<<<<<< HEAD
				if(esHoja(dato1))//si el izquierdo es hoja;
					if(op == "ADD" || op == "MOV")//es operacion conmutativa
>>>>>>> 9acbfaf... comentario
					{
						comAssembler.addMsg(op+" "+dato2+", "+dato1);//operacion sobre el mismo registro
						setAlmacenamiento(dato2);
					}
					else
					{if (op == "IMUL") {
						if(dato2.length() == 2) {
							registros.getReg("AX",this, comAssembler);
							registros.getReg("DX",this, comAssembler);
							dato1 = getHijoIzq().getAlmacenamiento();
							dato2 = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV AX, " + dato2);
							comAssembler.addMsg("IMUL AX, " + dato1);
							String reg = registros.getRegFreeLong(this);
							comAssembler.addMsg("MOV " + reg + ", DX:AX");
							registros.setRegTable("AX",false);
							registros.setRegTable("DX",false);
							setAlmacenamiento(reg);
						}else {
							registros.getReg("EAX",this, comAssembler);
							registros.getReg("EDX",this, comAssembler);
							dato1 = getHijoIzq().getAlmacenamiento();
							dato2 = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV EAX, " + dato2);
							comAssembler.addMsg("IMUL EAX, " + dato1);
							setAlmacenamiento("EAX");
							registros.setRegTable("EDX",false);
						}
					}else {
						String reg;
						if(dato2.length() == 2)
							reg = registros.getRegFreeInt(this);
						else {
							reg = registros.getRegFreeLong(this);
							comAssembler.addMsg("MOV " + reg + ", " + dato1);
							comAssembler.addMsg(op + " " + reg + ", " + dato2);
							setAlmacenamiento(reg);
							registros.setRegTable(dato2,false);
						}
					}	
					}
				else
				{
<<<<<<< HEAD
					if(matchDer.matches())
					{	//si el derecho es hoja;
						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
						//comInterm.addMsg();
					}
=======
					if(esHoja(dato2))	//si el derecho es hoja;
						if(op == "IMUL") {
							if(dato1.length() == 2) {
								registros.getReg("AX",this, comAssembler);
								registros.getReg("DX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dato1);
								comAssembler.addMsg("IMUL AX, " + dato2);
=======
				if((getHijoIzq() != null) && ((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
					if(operation.equals("ADD") || operation.equals("MOV")) { //es operacion conmutativa
						comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft); //operacion sobre el mismo registro
=======
			} else
				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) ///// HIJO IZQ ES CONST O VAR /////
=======
			} else { ///// UNO DE LOS HIJOS NO ES CONST O VAR /////
				if((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) { ///// HIJO IZQ ES CONST O VAR /////
>>>>>>> 88b2c34... _
					if(operation.equals("ADD") || operation.equals("MOV")) { ///// OPERACI�N CONMUTATIVA /////
						comAssembler.addMsg(operation + " " + dataFromRight + ", " + dataFromLeft);
>>>>>>> bca257b... resueltos problemas en common
						setAlmacenamiento(dataFromRight);
					} else { ///// OPERACI�N NO CONMUTATIVA /////
<<<<<<< HEAD
						if (operation.equals("IMUL")) {
							if(dataFromRight.length() == 2) {
<<<<<<< HEAD
								registros.getReg(RegisterTable.NAME_AX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_DX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_AX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX,this, comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
						if (operation.equals("IMUL")) { ///// MULTIPLICACI�N /////
							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX) { ///// ES ENTERA /////
								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromRight);
								comAssembler.addMsg("IMUL AX, " + dataFromLeft);
								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
								registros.freeReg(RegisterTable.AX);
								registros.freeReg(RegisterTable.DX);
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								setAlmacenamiento(reg);
<<<<<<< HEAD
<<<<<<< HEAD
							} else {
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
							}else {
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
							} else { ///// ES LONG /////
								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromRight);
								comAssembler.addMsg("IMUL EAX, " + dataFromLeft);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
								registros.freeReg(RegisterTable.EDX);
							}
						} else { ///// NO ES MULTIPLICACI�N /////
							String reg;
<<<<<<< HEAD
<<<<<<< HEAD
							if(!(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX))
								reg = registros.getRegFreeInt(this);
							else
								reg = registros.getRegFreeLong(this);
=======
							if(dataFromRight == RegisterTable.NAME_AX || dataFromRight == RegisterTable.NAME_BX || dataFromRight == RegisterTable.NAME_CX || dataFromRight == RegisterTable.NAME_DX)
								reg = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
							else
								reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
							dataFromRight = getHijoDer().getAlmacenamiento();
							comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
							comAssembler.addMsg(operation + " " + reg + ", " + dataFromRight);
							setAlmacenamiento(reg);
							registros.freeReg(registros.getRegPos(dataFromRight));
						}
=======
							if(dataFromRight.length() == 2)
								reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
							else {
								reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
								comAssembler.addMsg("MOV " + reg + ", " + dataFromLeft);
								comAssembler.addMsg(operation + " " + reg + ", " + dataFromRight);
								setAlmacenamiento(reg);
								registros.freeReg(registros.getRegPos(dataFromRight));
							}
						}	
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
					}
				} else { ///// HIJO IZQ NO ES VAR O CONST
					if (((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()) { ///// EL HIJO DER ES CONST O VAR /////
						if(operation == "IMUL") { ///// MULTIPLICACI�N /////
							if(dataFromLeft == RegisterTable.NAME_AX || dataFromLeft == RegisterTable.NAME_BX || dataFromLeft == RegisterTable.NAME_CX || dataFromLeft == RegisterTable.NAME_DX) { ///// ENTERO /////
								registros.getReg(RegisterTable.NAME_AX,getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX,getHijoIzq(), comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromLeft);
								comAssembler.addMsg("IMUL AX, " + dataFromRight);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
								String reg = registros.getRegFreeLong(this);
=======
								String reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
<<<<<<< HEAD
								registros.setRegTable("AX",false);
								registros.setRegTable("DX",false);
=======
=======
								String reg = registros.getRegFreeLong(getHijoIzq(),symbolTable,comAssembler); // -----
								comAssembler.addMsg("MOV " + reg + ", DX:AX"); 
>>>>>>> 88b2c34... _
								registros.freeReg(RegisterTable.AX);
								registros.freeReg(RegisterTable.DX);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
>>>>>>> bca257b... resueltos problemas en common
								setAlmacenamiento(reg);
<<<<<<< HEAD
							}else {
<<<<<<< HEAD
<<<<<<< HEAD
								registros.getReg("EAX",this, comAssembler);
								registros.getReg("EDX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dato1);
								comAssembler.addMsg("IMUL EAX, " + dato2);
								setAlmacenamiento("EAX");
								registros.setRegTable("EDX",false);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
							} else { ///// LONG /////
								registros.getReg(RegisterTable.NAME_EAX,getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromLeft);
								comAssembler.addMsg("IMUL EAX, " + dataFromRight);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								registros.freeReg(RegisterTable.EDX);
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
=======
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
>>>>>>> bca257b... resueltos problemas en common
							}
						} else { ///// NO ES MULTIPLICACI�N /////
							comAssembler.addMsg(operation+" "+dataFromLeft+", "+dataFromRight);
							setAlmacenamiento(dataFromLeft);
						}
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 154a393... comentario
					else//ninguno es hoja
					{
<<<<<<< HEAD
						if(op == "IMUL") {
							if(dato1.length() == 2) {
								registros.getReg("AX",this, comAssembler);
								registros.getReg("DX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dato1);
								comAssembler.addMsg("IMUL AX, " + dato2);
=======
=======
					else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
>>>>>>> bca257b... resueltos problemas en common
=======
					} else { ///// NINGUNO DE SUS HIJOS ES CONST O VAR /////
>>>>>>> 88b2c34... _
						if(operation == "IMUL") {
							if(dataFromLeft.length() == 2) {
								registros.getReg(RegisterTable.NAME_AX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_DX, getHijoIzq(), comAssembler,symbolTable);
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV AX, " + dataFromLeft);
								comAssembler.addMsg("IMUL AX, " + dataFromRight);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> a091e6e... arreglos por punteros null
								String reg = registros.getRegFreeLong(this);
=======
								String reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
								String reg = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler); // ----
>>>>>>> 88b2c34... _
								comAssembler.addMsg("MOV " + reg + ", DX:AX");
								registros.setRegTable("AX",false);
								registros.setRegTable("DX",false);
								setAlmacenamiento(reg);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
							}else {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
								registros.getReg("EAX",this, comAssembler);
								registros.getReg("EDX",this, comAssembler);
								dato1 = getHijoIzq().getAlmacenamiento();
								dato2 = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dato1);
								comAssembler.addMsg("IMUL EAX, " + dato2);
								setAlmacenamiento("EAX");
								registros.setRegTable("EDX",false);
							}
						} else {
							comAssembler.addMsg(op+" "+dato1+", "+dato2);
							setAlmacenamiento(dato1);
							registros.setRegTable(dato2, false);//datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler);
=======
								registros.getReg(RegisterTable.NAME_EAX,this, comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX,this, comAssembler,symbolTable);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
=======
								registros.getReg(RegisterTable.NAME_EAX, getHijoIzq(), comAssembler,symbolTable);
								registros.getReg(RegisterTable.NAME_EDX, getHijoIzq(), comAssembler,symbolTable);
>>>>>>> 88b2c34... _
								dataFromLeft = getHijoIzq().getAlmacenamiento();
								dataFromRight = getHijoDer().getAlmacenamiento();
								comAssembler.addMsg("MOV EAX, " + dataFromLeft);
								comAssembler.addMsg("IMUL EAX, " + dataFromRight);
								setAlmacenamiento(RegisterTable.NAME_EAX);
								registros.freeReg(RegisterTable.EDX);
								if (!(((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromLeft));
								if (!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
									registros.freeReg(registros.getRegPos(dataFromRight));
							}
						} else {
							comAssembler.addMsg(operation + " " + dataFromLeft + ", " + dataFromRight);
							setAlmacenamiento(dataFromLeft);
							registros.freeReg(registros.getRegPos(dataFromRight)); //datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
>>>>>>> a091e6e... arreglos por punteros null
						}
					}
				}
<<<<<<< HEAD
<<<<<<< HEAD
		}
		else 
		{
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		String datoIzq;
		String datoDer;
		if (getHijoIzq() != null)
			datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
		if (getHijoDer() != null)
			datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");

//		String op="";
//
//		switch(super.getElem()){
//		case "+":
//			op="ADD";
//		case "-":
//			op="SUB";
//		case "*":
//			op="IMUL";
//		case "/":
//		{
//			op="IDIV";
//			comAssembler.addMsg("CMP " + datoDer + ", " + 0);
//			comAssembler.addMsg("JZ _DivisionPorCero");//Salto al error del programa si el divisor es 0
//
//			/*contEtiquetas++;
//
//					comAssembler.addMsg("CMP " + datoDer + ", " + 0);
//					comAssembler.addMsg("JZ _label" + contEtiquetas);//Salto al error del programa si el divisor es 0
//				/*	comAssembler.addMsg("_label" + contEtiquetas + ":");
//					comAssembler.addMsg("invoke StdOut, addr _Errorlabel" + contEtiquetas);
//
//					ElementoTS tupla = new ElementoTS("_Errorlabel"+contEtiquetas, "", "Error: division por cero");
//					symbolTable.put("_Errorlabel"+contEtiquetas, tupla);
//
//					contEtiquetas++;
//
//					comAssembler.addMsg("JMP _label"+contEtiquetas);//salto al final del programa
//					comAssembler.addMsg("_label"+contEtiquetas+":");
//
//					comAssembler.addMsg("invoke ExitProcess, "+0);*/
//		}
//		case ":=":
//		{
//			op="MOV";
//		}
//		default://si los dos son hojas -- en cualquier otro caso
//		{
//			op="CMP";
//		}
//		}
//		if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/") {
//			if(esHoja(datoIzq) && esHoja(datoDer)){//si los dos son hojas
//				if((op == "IMUL")) {//si es operacion de multiplicacion
//					String reg;
//					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int") {
//						comAssembler.addMsg("MOV AX, "+datoIzq);				//devuelvo codigo assembler correspondiente
//						comAssembler.addMsg("IMUL AX, "+datoDer);
//						reg = registros.getRegFreeLong();
//						comAssembler.addMsg("MOV " + reg + ", DX:AX");
//						return reg;
//					}else {
//						comAssembler.addMsg("MOV EAX, "+datoIzq);				//devuelvo codigo assembler correspondiente
//						comAssembler.addMsg("IMUL EAX, "+datoDer);
//						reg = registros.getRegFreeLong();
////						comAssembler.addMsg(/*comando para reducir a EAX el contenido de EDX:EAX*/); //L�nea comentada para poder compilar
//						comAssembler.addMsg("MOV " + reg + ", EAX");
//						return reg;
//					}
//				}else { //si no es operacion de multiplicacion
//					String reg;
//					if(symbolTable.get(datoIzq.substring(0)).getTipoAtributo() == "int")
//						reg = registros.getRegFreeInt();//obtener algun registro int libre
//					else
//						reg = registros.getRegFreeLong();//obtener algun registro long libre
//
//					comAssembler.addMsg("MOV "+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
//					comAssembler.addMsg(op+" "+reg+", "+datoDer);
//					return reg;
//				}
//			}
//			else
//				if(esHoja(datoIzq))//si el izquierdo es hoja;
//					if(op == "ADD" || op == "MOV")//es operacion conmutativa
//					{
//						comAssembler.addMsg(op+" "+datoDer+", "+datoIzq);//operacion sobre el mismo registro
//						return datoDer;
//					}
//					else
//					{if (op == "IMUL") {
//
//					}else {
//
//					}	
//					//						int reg=registros.getRegFree();//obtener algun registro libre
//					//						boolean state=true;
//					//						registros.setRegTable(reg, state);
//					//						comAssembler.addMsg("MOV R"+reg+", "+datoDer);							//devuelvo codigo assembler correspondiente
//					//						comAssembler.addMsg(op+" "+reg+", "+datoIzq);
//					//comInterm.addMsg();
//					//registros.setRegTable(datoIzq, false); datoIzq es un String de la forma R1 , hay que transformarlo a numero no mas
//					//						return "R"+reg;
//					}
//				else
//				{
//					if(esHoja(datoDer))
//					{	//si el derecho es hoja;
//						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
//						//comInterm.addMsg();
//					}
//					else//ninguno es hoja
//					{
//						comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
//						//registros.setRegTable(datoDer, false);datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
//						//comInterm.addMsg();
//					}
//					return datoIzq;
//				}
//		}
//		else 
//		{
//			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!="))
//				comAssembler.addMsg("CMP " + datoIzq +", " + datoDer);
//
//			return super.getElem();//_S, IF, Cuerpo, comparadores, 
//		}
//		return datoDer;
		return "";
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
			if((super.getElem() == "<")||(super.getElem() == ">")||(super.getElem() == "<=")||(super.getElem() == ">=")||(super.getElem() == "==")||(super.getElem() == "!=")) {
				if(esHoja(dato1)) {
					if(symbolTable.get(dato1.substring(1)).getTipoAtributo() == "int") 
						dato1 = registros.getRegFreeInt(this);
=======
			if((getElem() == "<")||(getElem() == ">")||(getElem() == "<=")||(getElem() == ">=")||(getElem() == "==")||(getElem() == "!=")) {
				if((getHijoIzq() != null) && (((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())) {
					if(symbolTable.get(dataFromLeft.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) 
<<<<<<< HEAD
						dataFromLeft = registros.getRegFreeInt(this);
>>>>>>> a091e6e... arreglos por punteros null
=======
=======
			}
>>>>>>> 88b2c34... _
		} else { ///// OPERACI�N DE COMPARACI�N /////
			if((getElem() == "<") || (getElem() == ">") || (getElem() == "<=") || (getElem() == ">=") || (getElem() == "==") || (getElem() == "!=")) {
				if ((int)(dataFromLeft.charAt(0)) >= 48 && (int)(dataFromLeft.charAt(0)) <= 57) {
					String regAux;
					if(isInt(dataFromLeft, symbolTable)) 
<<<<<<< HEAD
						regAux = registros.getRegFreeInt(this);
>>>>>>> bca257b... resueltos problemas en common
					else
						regAux = registros.getRegFreeLong(this);
=======
						regAux = registros.getRegFreeInt(getHijoIzq(), symbolTable, comAssembler);
					else
						regAux = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
					comAssembler.addMsg("MOV " + regAux + ", " + dataFromLeft);
					dataFromLeft = regAux;
				}
<<<<<<< HEAD
<<<<<<< HEAD
				comAssembler.addMsg("CMP " + dato1 +", " + dato2);
				registros.setRegTable(dato1,false);
				if(!esHoja(dato2)) {
					registros.setRegTable(dato2,false);
=======
						dataFromLeft = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						dataFromLeft = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
				}
				setAlmacenamiento(super.getElem());//_S, IF, Cuerpo, comparadores, 
=======
=======
				dataFromRight = getHijoDer().getAlmacenamiento();
>>>>>>> bca257b... resueltos problemas en common
				comAssembler.addMsg("CMP " + dataFromLeft +", " + dataFromRight); 
				registros.freeReg(registros.getRegPos(dataFromLeft));
				if(!(((SyntacticTreeLeaf)getHijoDer()).isVariableOrConst()))
					registros.freeReg(registros.getRegPos(dataFromRight));
<<<<<<< HEAD
				setAlmacenamiento(getElem());//_S, IF, Cuerpo, comparadores, 
>>>>>>> a091e6e... arreglos por punteros null
=======
				if(!((((SyntacticTreeLeaf)getHijoIzq()).isVariableOrConst())))
					registros.freeReg(registros.getRegPos(dataFromLeft));
<<<<<<< HEAD
				setAlmacenamiento(getElem()); //_S, IF, Cuerpo, comparadores, 
>>>>>>> bca257b... resueltos problemas en common
=======
				setAlmacenamiento(getElem()); //_S, If, Cuerpo, Comparadores, 
>>>>>>> 6bb5a8f... _
			}
		}
>>>>>>> 154a393... comentario
	}

<<<<<<< HEAD
<<<<<<< HEAD
	public boolean esHoja(String dato) {
<<<<<<< HEAD
		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9')&&(dato.charAt(0) >= '0')));
=======
		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9') && (dato.charAt(0) >= '0'))); //TODO: ??????????
>>>>>>> 1375c5c... arreglos varios
	}
>>>>>>> 9acbfaf... comentario
=======
//	public boolean esHoja(String dato) {
//		return ((dato.charAt(0) == '_') || ((dato.charAt(0) <= '9') && (dato.charAt(0) >= '0')));
//	}
>>>>>>> a091e6e... arreglos por punteros null
=======
	private boolean isInt(String data, Hashtable<String, ElementoTS> symbolTable) {
		boolean result = (data == RegisterTable.NAME_AX) || (data == RegisterTable.NAME_BX) || (data == RegisterTable.NAME_CX) || (data == RegisterTable.NAME_DX);
		if (symbolTable.get(data) != null)
			result = result || (symbolTable.get(data).getTipoAtributo().equals(ElementoTS.INT));
		if (symbolTable.get(data.substring(1)) != null)
			result = result || (symbolTable.get(data.substring(1)).getTipoAtributo().equals(ElementoTS.INT));
		return result;
	}
>>>>>>> bca257b... resueltos problemas en common
}
