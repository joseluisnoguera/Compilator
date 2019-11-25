package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class ST_Memory extends SyntacticTree{
	public ST_Memory(String lexeme, SyntacticTree nodoIzq, SyntacticTree nodoDer) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodoIzq);
		super.setHijoDer(nodoDer);
	}
<<<<<<< HEAD

	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
		String dato1 = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable);
		String dato2 = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable);
=======
	
	@Override
<<<<<<< HEAD:src/utils/syntacticTree/SyntacticTreeMemory.java
<<<<<<< HEAD
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		String dato1 = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, deep+1);
		String dato2 = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, deep+1);
>>>>>>> 7eacf2b... _
=======
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, 
=======
	public void recorreArbol(RegisterTable registers, MsgStack comAssembler, MsgStack comInterm, 
>>>>>>> f58785c... arreglos para condiciones en indice de foreach:src/utils/syntacticTree/ST_Memory.java
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
<<<<<<< HEAD
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
		super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
		String dato1 = getHijoIzq().getAlmacenamiento();
		String dato2 = getHijoDer().getAlmacenamiento();
>>>>>>> 154a393... comentario
		String op = super.getElem();
		String reg = registros.getRegFreeLong(this);//usa un registro
=======
		comInterm.addMsg(blankPrefix + "Nodo: " + getElem());
		String dataFromLeft = "";
		String dataFromRight = "";
		System.out.println("en memory: " + getElem());
		getHijoIzq().recorreArbol(registers, comAssembler, comInterm, symbolTable, blankPrefix + getBlankSpace());
		getHijoDer().recorreArbol(registers, comAssembler, comInterm, symbolTable, blankPrefix + getBlankSpace());
		dataFromLeft = getHijoIzq().getAlmacenamiento();
		dataFromRight = getHijoDer().getAlmacenamiento(); 
<<<<<<< HEAD
<<<<<<< HEAD
		String op = getElem();
<<<<<<< HEAD
		String reg = registros.getRegFreeLong(this);
>>>>>>> 1375c5c... arreglos varios
=======
		String reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
>>>>>>> 20a16f7... Update -
		switch(op) {
<<<<<<< HEAD
		case "::=":{
<<<<<<< HEAD
			comAssembler.addMsg("mov " + reg + ", offset " + dato2);
			comAssembler.addMsg("mov " + dato1 + ", " + reg);
		}
		case "<<":{
<<<<<<< HEAD
			String lexico = dato1.substring(1);
			comAssembler.addMsg("mov " + reg + ", " + dato2);//reg tiene la direccion del arreglo a la que apunta i
			String reg2 = registros.getRegFreeLong();
			comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(lexico).getCSize());//reg 2 tiene la cant de datos del arreglo
			if (symbolTable.get(lexico).getTipoAtributo() == "int") {
				comAssembler.addMsg("mul " + reg2 + ", 2");
			}else {
				comAssembler.addMsg("mul " + reg2 + ", 4");
			}//reg 2 tiene el largo del arreglo en bytes
			String reg3 = registros.getRegFreeLong();
			comAssembler.addMsg("mov " + reg + ", offset " + dato2);//reg3 tiene la direccion inicial del arreglo
			comAssembler.addMsg("add " + reg2 + ", " + reg3);//reg2 tiene la direccion fin del arreglo
			comAssembler.addMsg("cmp " + reg + ", " + reg2);//se compara la direccion a la que apunta i con direccion fin del arreglo
			registros.setRegTable(reg2,false);//libera el registro usado
			registros.setRegTable(reg3,false);//libera el registro usado
		}
		case "+=":{
			comAssembler.addMsg("mov " + reg + ", " + dato1);
			String lexico = dato1.substring(1);
			if (symbolTable.get(lexico).getTipoAtributo() == "int") {
=======
			comAssembler.addMsg("mov " + reg + ", " + dato1); //reg tiene la direccion del arreglo a la que apunta i
<<<<<<< HEAD
=======
			comAssembler.addMsg("mov " + reg + ", offset " + dataFromRight); //posicion inicial del arreglo
			comAssembler.addMsg("mov " + dataFromLeft + ", " + reg);
		}
		case "<<":{
			comAssembler.addMsg("mov " + reg + ", " + dataFromLeft); //reg tiene la direccion del arreglo a la que apunta i
<<<<<<< HEAD
>>>>>>> bca257b... resueltos problemas en common
			String reg2 = registros.getRegFreeLong(this);
			comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(dataFromLeft.substring(1)).getCSizeBytes()); //reg 2 tiene el largo del arreglo en bytes
			String reg3 = registros.getRegFreeLong(this);
=======
			String reg2 = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
			comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(dataFromLeft.substring(1)).getCSizeBytes()); //reg 2 tiene el largo del arreglo en bytes
			String reg3 = registros.getRegFreeLong(getHijoDer(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
			comAssembler.addMsg("mov " + reg3 + ", offset " + dataFromRight); //reg3 tiene la direccion inicial del arreglo
=======
			String reg2 = registros.getRegFreeLong(this,symbolTable,comAssembler);
			comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(dato1.substring(1)).getCSizeBytes()); //reg 2 tiene el largo del arreglo en bytes
			String reg3 = registros.getRegFreeLong(this,symbolTable,comAssembler);
			comAssembler.addMsg("mov " + reg3 + ", offset " + dato2); //reg3 tiene la direccion inicial del arreglo
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			comAssembler.addMsg("add " + reg2 + ", " + reg3); //reg2 tiene la direccion fin del arreglo
			comAssembler.addMsg("cmp " + reg + ", " + reg2); //se compara la direccion a la que apunta i con direccion fin del arreglo
			registros.freeReg(registros.getRegPos(reg2));
			registros.freeReg(registros.getRegPos(reg3));
		}
		case "+=":{
<<<<<<< HEAD
			comAssembler.addMsg("mov " + reg + ", " + dato1);
			if (symbolTable.get(dato2.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
>>>>>>> 1375c5c... arreglos varios
=======
			comAssembler.addMsg("mov " + reg + ", " + dataFromLeft);
			if (symbolTable.get(dataFromRight.substring(1)).getTipoAtributo().equals(ElementoTS.INT)) {
>>>>>>> bca257b... resueltos problemas en common
				comAssembler.addMsg("add " + reg + ", 2");
			}else {
				comAssembler.addMsg("add " + reg + ", 4");
=======
			case "::=":{
				comAssembler.addMsg("mov " + reg + ", offset " + dato2);//posicion inicial del arreglo
				comAssembler.addMsg("mov " + dato1 + ", " + reg);
			}
			case "<<":{
				String lexico = dato1.substring(1);
				comAssembler.addMsg("mov " + reg + ", " + dato1);//reg tiene la direccion del arreglo a la que apunta i
				String reg2 = registros.getRegFreeLong(this);
				comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(lexico).getCSizeBytes());//reg 2 tiene el largo del arreglo en bytes
				String reg3 = registros.getRegFreeLong(this);
				comAssembler.addMsg("mov " + reg3 + ", offset " + dato2);//reg3 tiene la direccion inicial del arreglo
				comAssembler.addMsg("add " + reg2 + ", " + reg3);//reg2 tiene la direccion fin del arreglo
				comAssembler.addMsg("cmp " + reg + ", " + reg2);//se compara la direccion a la que apunta i con direccion fin del arreglo
				registros.setRegTable(reg2,false);//libera el registro usado
				registros.setRegTable(reg3,false);//libera el registro usado
			}
			case "+=":{
				comAssembler.addMsg("mov " + reg + ", " + dato1);
				String lexico = dato2.substring(1);
				if (symbolTable.get(lexico).getTipoAtributo() == "int") {
					comAssembler.addMsg("add " + reg + ", 2");
				}else {
					comAssembler.addMsg("add " + reg + ", 4");
				}
				comAssembler.addMsg("mov " + dato1 + ", " + reg);
>>>>>>> 3c084f9... Update SyntacticTreeMemory-Leaf
=======
		String collectionPtr = registros.getRegFreeLong(this, symbolTable, comAssembler);
=======

>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
		switch(getElem()) {
		case "::=": {
			String collectionPtr = registers.getRegFreeLong(this, symbolTable, comAssembler);
			comAssembler.addMsg("lea " + collectionPtr + ", " + dataFromRight); //offset del arreglo en i
			comAssembler.addMsg("mov " + dataFromLeft + ", " + collectionPtr);
			registers.freeReg(registers.getRegPos(collectionPtr));
			break;
		}
		case "<<": {
			String collectionPtr = registers.getRegFreeLong(this, symbolTable, comAssembler);
			comAssembler.addMsg("mov " + collectionPtr + ", " + dataFromLeft); // lado izq tiene la direccion del arreglo
			String collectionSize = registers.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
			System.out.println("nombre de colecci�n: " + dataFromRight.substring(1));
			comAssembler.addMsg("mov " + collectionSize + ", " + symbolTable.get(dataFromRight.substring(1)).getCSizeBytes()); // obtiene el largo del arreglo en bytes
			String collectionOffset = registers.getRegFreeLong(getHijoDer(), symbolTable, comAssembler);
			comAssembler.addMsg("lea " + collectionOffset + ", " + dataFromRight); // obtiene el offset del arreglo
			comAssembler.addMsg("add " + collectionSize + ", " + collectionOffset); //direccion final del arreglo
			comAssembler.addMsg("cmp " + collectionPtr + ", " + collectionSize); //se compara la direccion a la que apunta i con direccion fin del arreglo
			registers.freeReg(registers.getRegPos(collectionSize));
			registers.freeReg(registers.getRegPos(collectionOffset));
			registers.freeReg(registers.getRegPos(collectionPtr));
			break;
		}
		case "+=": {
			if (getHijoDer().getType().equals(ElementoTS.INT)) {
				comAssembler.addMsg("add " + dataFromLeft + ", 2");
			} else {
<<<<<<< HEAD
				comAssembler.addMsg("add " + collectionPtr + ", 4");
>>>>>>> 51f241d... arreglos varios
=======
				comAssembler.addMsg("add " + dataFromLeft + ", 4");
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
			}
			break;
		}
		}
<<<<<<< HEAD
<<<<<<< HEAD
		registros.setRegTable(reg,false);//libera el registro usado
<<<<<<< HEAD
		return op;
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, 
			Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		String dato1 = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
		String dato2 = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, blankPrefix + " ");
//		String op = super.getElem();
//		String reg = registros.getRegFreeLong();//usa un registro
//		switch(op) {
//			case "::=":{
//				comAssembler.addMsg("mov " + reg + ", offset " + dato2);//posicion inicial del arreglo
//				comAssembler.addMsg("mov " + dato1 + ", " + reg);
//			}
//			case "<<":{
//				String lexico = dato1.substring(1);
//				comAssembler.addMsg("mov " + reg + ", " + dato1);//reg tiene la direccion del arreglo a la que apunta i
//				String reg2 = registros.getRegFreeLong();
//				comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(lexico).getCSizeBytes());//reg 2 tiene el largo del arreglo en bytes
//				String reg3 = registros.getRegFreeLong();
//				comAssembler.addMsg("mov " + reg3 + ", offset " + dato2);//reg3 tiene la direccion inicial del arreglo
//				comAssembler.addMsg("add " + reg2 + ", " + reg3);//reg2 tiene la direccion fin del arreglo
//				comAssembler.addMsg("cmp " + reg + ", " + reg2);//se compara la direccion a la que apunta i con direccion fin del arreglo
//				registros.setRegTable(reg2,false);//libera el registro usado
//				registros.setRegTable(reg3,false);//libera el registro usado
//			}
//			case "+=":{
//				comAssembler.addMsg("mov " + reg + ", " + dato1);
//				String lexico = dato2.substring(1);
//				if (symbolTable.get(lexico).getTipoAtributo() == "int") {
//					comAssembler.addMsg("add " + reg + ", 2");
//				}else {
//					comAssembler.addMsg("add " + reg + ", 4");
//				}
//				comAssembler.addMsg("mov " + dato1 + ", " + reg);
//			}
//		}
//		registros.setRegTable(reg,false);//libera el registro usado
//		return op;
		return "";
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
		setAlmacenamiento(op);
>>>>>>> 154a393... comentario
=======
		registros.freeReg(registros.getRegPos(collectionPtr));
<<<<<<< HEAD
		setAlmacenamiento("?");
>>>>>>> 51f241d... arreglos varios
=======
=======
>>>>>>> f406b63... guardado dx:ax en registro de 32 bits
		setAlmacenamiento(getElem());
>>>>>>> 446d784... comentario
	}
}
