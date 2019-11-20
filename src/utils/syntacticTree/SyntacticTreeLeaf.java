package utils.syntacticTree;

import java.util.Hashtable;

import utils.ElementoTS;
import utils.MsgStack;
import utils.RegisterTable;

public class SyntacticTreeLeaf extends SyntacticTree{

	public SyntacticTreeLeaf(String lex)
	{
		super(lex);
	}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable, int deep) {
		//TODO: Agregar blancos
		comInterm.addMsg("Nodo: " + super.getElem());
		return super.getElem();
=======
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
=======
	@Override
	public void recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String,
			ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
>>>>>>> 154a393... comentario
		String dato=super.getElem();
		if(dato.substring(dato.length()-1)=="]" )//obtiene nombre de la coleccion
		{
			int finColec=1;
			for(int i=0;i<dato.length()-1;i++)
			{
				if(dato.substring(i,i)=="[")//fin de la coleccion
				{
					finColec=i-1;
					break;
				}
			}
<<<<<<< HEAD
			
			String index=dato.substring(finColec+1,dato.length()-2);
<<<<<<< HEAD
=======
		
			String index=dato.substring(finColec+2,dato.length()-2);//contiene nombre de subindice
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			String regI=registros.getRegFreeLong();
			String subindice=dato.substring(finColec+2,finColec+2);
			if(subindice=="_")//es id
=======
			String regI=registros.getRegFreeLong(this);
			if(dato.substring(finColec+1,finColec+1)=="_")//es id
>>>>>>> 154a393... comentario
			{
				
				comAssembler.addMsg("mov "+regI+", "+index);//guarda en reg el valor asociado a index
				
			}
<<<<<<< HEAD
<<<<<<< HEAD
			
			String regColec = registros.getRegFreeLong();
=======

			String regColec = registros.getRegFreeLong(this);
>>>>>>> 154a393... comentario
			String colec=dato.substring(0,finColec);
=======

			String regColec = registros.getRegFreeLong();//guarda un registro para contener la coleccion
			String colec=dato.substring(0,finColec);//contiene el nombre de la coleccion
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			if(symbolTable.get(colec).getTipoAtributo()=="int")
			{
				comAssembler.addMsg("mul "+regI+", 2");//guardo en regI la direccion de memoria del registro
			}
			else
			{
				comAssembler.addMsg("mul "+regI+", 4");
			
			}
			comAssembler.addMsg("mov "+regColec+", offset "+colec);//guarda inicio de arreglo
			comAssembler.addMsg("add "+regI+", "+regColec);//guarda direc de memoria que se posiciona index
			comAssembler.addMsg("mov "+regColec+", dword ptr ["+regI+"]");//guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
			registros.setRegTable(regI,false);//libera el registro usado
			dato=regColec;
		}
<<<<<<< HEAD
<<<<<<< HEAD
		
=======
		else
		{
			
		}
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
		return dato;
>>>>>>> 3c084f9... Update SyntacticTreeMemory-Leaf
=======
	@Override
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String,
			ElementoTS> symbolTable, String blankPrefix) {
		comInterm.addMsg(blankPrefix + "Nodo: " + super.getElem());
//		String dato=super.getElem();
//		if(dato.substring(dato.length()-1)=="]" )
//		{
//			String lexico="";
//			int finColec=1;
//			for(int i=1;i<dato.length()-1;i++)
//			{
//				if(dato.substring(1,i)!="[")
//				{
//					lexico=dato.substring(1, i)+lexico;//contiene el id del arreglo
//					finColec=i;
//				}
//				else
//					break;
//			}
//
//			String index=dato.substring(finColec+1,dato.length()-2);
//			String regI=registros.getRegFreeLong();
//			if(dato.substring(finColec+1,finColec+1)=="_")//es id
//			{
//
//				comAssembler.addMsg("mov "+regI+", "+index);//guarda en reg el valor asociado a index
//
//			}
//
//			String regColec = registros.getRegFreeLong();
//			String colec=dato.substring(0,finColec);
//			if(symbolTable.get(colec).getTipoAtributo()=="int")
//			{
//				comAssembler.addMsg("mul "+regI+", 2");//guardo en regI la direccion de memoria del registro
//			}
//			else
//			{
//				comAssembler.addMsg("mul "+regI+", 4");
//
//			}
//			comAssembler.addMsg("mov "+regColec+", offset "+colec);//guarda inicio de arreglo
//			comAssembler.addMsg("add "+regI+", "+regColec);//guarda direc de memoria que se posiciona index
//			comAssembler.addMsg("mov "+regColec+", dword ptr ["+regI+"]");//guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
//			registros.setRegTable(regI,false);//libera el registro usado
//			registros.setRegTable(regColec,false);//libera el registro usado
//			dato=regColec;
//		}
//		return dato;
		return "";
>>>>>>> 45299ea... visualización de árbol sintáctico
=======

		setAlmacenamiento(dato);
>>>>>>> 154a393... comentario
	}
}

