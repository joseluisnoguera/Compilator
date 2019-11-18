package utils;

import java.util.Hashtable;

public class SintacticTreeMemory extends SintacticTree{
	public SintacticTreeMemory(String lexeme, SintacticTree nodoIzq, SintacticTree nodoDer) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodoIzq);
		super.setHijoDer(nodoDer);
	}
	
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
		String dato1 = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm, symbolTable);
		String dato2 = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm, symbolTable);
		String op = super.getElem();
		String reg = registros.getRegFreeLong();//usa un registro
		switch(op) {
			case "::=":{
				comAssembler.addMsg("mov " + reg + ", offset " + dato2);
				comAssembler.addMsg("mov " + dato1 + ", " + reg);
			}
			case "<<":{
				String lexico = dato1.substring(1);
				comAssembler.addMsg("mov " + reg + ", " + dato1);//reg tiene la direccion del arreglo a la que apunta i
				String reg2 = registros.getRegFreeLong();
				comAssembler.addMsg("mov " + reg2 + ", " + symbolTable.get(lexico).getCSizeBytes());//reg 2 tiene el largo del arreglo en bytes
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
				}
				comAssembler.addMsg("mov " + dato1 + ", " + reg);
			}
		}
		registros.setRegTable(reg,false);//libera el registro usado
		return op;
	}
}
