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
<<<<<<< HEAD
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			String regI=registros.getRegFreeLong();
=======
			String regI=registros.getRegFreeLong(this);
>>>>>>> e149002... Update SyntacticTreeLeaf.java
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

			String regColec = registros.getRegFreeLong(this);//guarda un registro para contener la coleccion
			String colec=dato.substring(0,finColec);//contiene el nombre de la coleccion
>>>>>>> df1f095... Update ElementoTS/AssGen/Leaf/Unary
			if(symbolTable.get(colec).getTipoAtributo()=="int")
			{
				comAssembler.addMsg("mul "+regI+", 2");//guardo en regI la direccion de memoria del registro
			}
<<<<<<< HEAD
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
			if(symbolTable.get(dato).getAccMemory())//chequea si el id es indice del foreach
			{
				String reg="";
				if(symbolTable.get(dato).getTipoAtributo()=="int")//chequea tipo del indice
				{
					reg=registros.getRegFreeInt(this);
				}
=======
			String index = dato.substring(finColec+2,dato.length()-2);//contiene nombre de subindice
<<<<<<< HEAD
<<<<<<< HEAD
			String regIndex = registros.getRegFreeLong(this);
			comAssembler.addMsg("mov " + regIndex + ", " + index);
			String regCollection = registros.getRegFreeLong(this);//guarda un registro para contener la coleccion
=======
			String regIndex = registros.getRegFreeLong(this,symbolTable,comAssembler);
=======
			String regIndex = registros.getRegFreeLong(getHijoIzq(), symbolTable, comAssembler);
>>>>>>> 88b2c34... _
			comAssembler.addMsg("mov " + regIndex + ", " + index);
			String regCollection = registros.getRegFreeLong(this,symbolTable,comAssembler);//guarda un registro para contener la coleccion
<<<<<<< HEAD
>>>>>>> 39b95a0... Update RegisterTable-VarAUX
			String collectionName = dato.substring(0,finColec); //Comienza en 1 para quitar el _ ya que en la TS no lo tiene
=======
			String collectionName = dato.substring(1,finColec); //Comienza en 1 para quitar el _ ya que en la TS no lo tiene
>>>>>>> 050f179... Update -

			if(symbolTable.get(collectionName).getTipoAtributo().equals("int"))
				comAssembler.addMsg("mul " + regIndex + ", 2");//guardo en regI la direccion de memoria del registro
			else
				comAssembler.addMsg("mul " + regIndex + ", 4");
			comAssembler.addMsg("mov " + regCollection + ", offset " + collectionName); //guarda inicio de arreglo
			comAssembler.addMsg("add " + regIndex + ", " + regCollection); //guarda direc de memoria que se posiciona index
			///////////////////////////////////////////////////////////////////////////////////////////////
			//chequeo en tiempo de ejecuci�n
			//chequeo por arreglo superado
			String regEndCollection = registros.getRegFreeLong(this,symbolTable,comAssembler);
			comAssembler.addMsg("MOV " + regEndCollection + ", " + symbolTable.get(collectionName).getCSizeBytes());//guarda el tama�o en bytes del arreglo
			comAssembler.addMsg("ADD " + regEndCollection + ", " + regCollection);//guarda la direccion final del arreglo
			comAssembler.addMsg("CMP " + regIndex + ", " + regEndCollection);//compara direccion final del arreglo con direccion a la que se desea acceder del arreglo
			comAssembler.addMsg("JG _ArregloFueraDeRango");
			//chequeo por direccion de memoria menor
			comAssembler.addMsg("CMP " + regIndex + ", " + regCollection);//compara direccion final del arreglo con direccion a la que se desea acceder del arreglo
			comAssembler.addMsg("JL _ArregloFueraDeRango");
			//fin chequeo en tiempo de ejecucion
			/////////////////////////////////////////////////////////////////////////////////////////
			comAssembler.addMsg("mov " + regCollection + ", dword ptr [" + regIndex + "]"); //guarda en regColec el valor almacenado en la direccion de memoria guardada en regI
			registros.freeReg(registros.getRegPos(regIndex));
			dato = regCollection;
			
		} else {
<<<<<<< HEAD
<<<<<<< HEAD
			System.out.println("Lexema completo: " + dato);
			System.out.println("Lexema: " + dato.substring(1));
			System.out.println("En TS: " + symbolTable.get(dato.substring(1)));
			System.out.println("Hash: " + symbolTable.toString());
			if(symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
				String reg = "";
				if(symbolTable.get(dato).getTipoAtributo().equals(ElementoTS.INT)) //chequea tipo del indice
					reg = registros.getRegFreeInt(this);
>>>>>>> 1375c5c... arreglos varios
				else
					reg=registros.getRegFreeLong(this);
				comAssembler.addMsg("mov "+reg+", dword ptr ["+dato+"]");//mueve a reg el dato almacenado en la direccion de memoria guardada en dato.
				dato=reg;
			}
=======
			if (dato.charAt(0) == '_')
				if(symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
=======
			if (dato.charAt(0) == '_' && symbolTable.get(dato.substring(1)).isPointer()) { //Si el id es indice del foreach
>>>>>>> 050f179... Update -
					String reg = "";
					if(symbolTable.get(dato).getTipoAtributo().equals("int")) //chequea tipo del indice
						reg = registros.getRegFreeInt(this,symbolTable,comAssembler);
					else
						reg = registros.getRegFreeLong(this,symbolTable,comAssembler);
					comAssembler.addMsg("mov " + reg + ", dword ptr [" + dato + "]");//mueve a reg el dato almacenado en la direccion de memoria guardada en dato.
					dato = reg;
				}
>>>>>>> a091e6e... arreglos por punteros null
		}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
		
		
>>>>>>> e149002... Update SyntacticTreeLeaf.java
=======
		setAlmacenamiento(dato);
>>>>>>> bca257b... resueltos problemas en common
=======
		setAlmacenamiento(dato);
>>>>>>> 050f179... Update -
	}
	
	public boolean isVariableOrConst() { return (getElem().charAt(0) == '_' || (int)(getElem().charAt(0)) >= 48 && (int)(getElem().charAt(0)) <= 57); }
	
}

