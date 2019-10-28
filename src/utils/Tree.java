package utils;

import java.util.Hashtable;
import java.util.LinkedList;

import utils.SintacticTree;

public class Tree {

	private LinkedList<SintacticTree> nodos;
	private Hashtable<String, ElementoTS> symbolTable;
	
	public Tree() {}
	
	public void insertNodo(SintacticTreeLeaf nodo)
	{
		nodos.push(nodo);
	}
	
	public void insertNodo(SintacticTreeCommon nodo) {
		if(nodos.size()==1) {//Cuando detecte la ultima sentencia en un bloque
			nodo.setHijoIzq(nodos.pop());
		}else {
			nodo.setHijoDer(nodos.pop());
			nodo.setHijoIzq(nodos.pop());
		}
		nodos.push(nodo);
	}
	
	public void insertNodo(SintacticTreeCtrl nodo) {
		nodo.setHijoIzq(nodos.pop());
		nodos.push(nodo);
	}
	
	public SintacticTree getRoot() {
		return nodos.peek();
	}
	
}
