package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeMemory extends SyntacticTree{
	public SyntacticTreeMemory(String lexeme, SyntacticTree nodoIzq, SyntacticTree nodoDer) {
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
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		String dato1 = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable, deep+1);
		String dato2 = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable, deep+1);
>>>>>>> 7eacf2b... _
		String op = super.getElem();
		String reg = registros.getRegFreeLong();//usa un registro
		switch(op) {
<<<<<<< HEAD
		case "::=":{
			comAssembler.addMsg("mov " + reg + ", offset " + dato2);
			comAssembler.addMsg("mov " + dato1 + ", " + reg);
		}
		case "<<":{
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
				String reg2 = registros.getRegFreeLong();
				comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(lexico).getCSizeBytes());//reg 2 tiene el largo del arreglo en bytes
				String reg3 = registros.getRegFreeLong();
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
			}
			comAssembler.addMsg("mov " + dato1 + ", " + reg);
		}
		}
		registros.setRegTable(reg,false);//libera el registro usado
		return op;
	}
}
