package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeUnary extends SyntacticTree{

	public SyntacticTreeUnary(String lexeme, SyntacticTree nodo) {
		super(lexeme);
		super.setHijoIzq(nodo);
	}

	@Override
<<<<<<< HEAD
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		comAssembler.addMsg("invoke StdOut, addr " + super.getElem());
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
<<<<<<< HEAD
<<<<<<< HEAD
//		comAssembler.addMsg("invoke StdOut, addr " + super.getElem());
>>>>>>> 45299ea... visualización de árbol sintáctico
=======
		String reg=registros.getRegEAX();
=======

		String reg=((Object) registros).getRegEAX();
>>>>>>> a3a7142... Update SyntacticTreeLeaf | Indice accMemory y dato
		comAssembler.addMsg("mov "+reg+", " + super.getElem());
		comAssembler.addMsg("call _print");
		
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
		return "";
=======
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
		comAssembler.addMsg("invoke StdOut, addr " + super.getElem());
		setAlmacenamiento("");
>>>>>>> 154a393... comentario
	}
}
