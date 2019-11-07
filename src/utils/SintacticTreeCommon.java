package utils;

import java.util.Hashtable;
import java.util.regex.*;

public class SintacticTreeCommon extends SintacticTree {

	public SintacticTreeCommon(String lexeme, SintacticTree nodoIzq, SintacticTree nodoDer) {
		super(lexeme);
		// TODO Auto-generated constructor stub
		super.setHijoIzq(nodoIzq);
		super.setHijoDer(nodoDer);
	}
	
	public String recorreArbol(RegisterTable registros, MsgStack comAssembler, MsgStack comInterm, Hashtable<String, ElementoTS> symbolTable) {
		String datoIzq = super.getHijoIzq().recorreArbol(registros, comAssembler, comInterm);
		String datoDer = super.getHijoDer().recorreArbol(registros, comAssembler, comInterm);
		
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
				op="MUL";
			case "/":
				{
					op="DIV";
					
					contEtiquetas++;
				
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
		if( matchIzq.matches() && matchDer.matches()) 
			
		{
			
			//si los dos son hojas
			int reg=registros.getRegFree();//obtener algun registro libre
			boolean state=true;
			registros.setRegTable(reg, state);
			comAssembler.addMsg("MOV R"+reg+", "+datoIzq);				//devuelvo codigo assembler correspondiente
			comAssembler.addMsg(op+" R"+reg+", "+datoDer);
			//devuelve lo que se devuelve por pantalla
			//comInterm.addMsg();
			return "R"+reg;
		}
		else
			if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()=="-" || super.getElem()==":=" || super.getElem()=="/")			
				
				
					if(matchIzq.matches())//si el izquierdo es hoja;
						if(super.getElem() == "+" || super.getElem() == "*" || super.getElem()==":=")//es operacion conmutativa
						{
							comAssembler.addMsg(op+" "+datoDer+", "+datoIzq);//operacion sobre el mismo registro
							return datoDer;
						}
						else
							{	
								int reg=registros.getRegFree();//obtener algun registro libre
								boolean state=true;
								registros.setRegTable(reg, state);
								comAssembler.addMsg("MOV R"+reg+", "+datoDer);							//devuelvo codigo assembler correspondiente
								comAssembler.addMsg(op+" "+reg+", "+datoIzq);
								//comInterm.addMsg();
								//registros.setRegTable(datoIzq, false); datoIzq es un String de la forma R1 , hay que transformarlo a numero no mas
								return "R"+reg;
							}
					else
						{
						if(matchDer.matches())
						{	//si el derecho es hoja;
							comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
							//comInterm.addMsg();
						}
						else
						{
							comAssembler.addMsg(op+" "+datoIzq+", "+datoDer);
							//registros.setRegTable(datoDer, false);datoDer es un String de la forma R1 , hay que transformarlo a numero no mas
							//comInterm.addMsg();
							
						
						}
						return datoIzq;
						}
				
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
}
