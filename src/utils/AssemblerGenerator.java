package utils;

import java.util.Hashtable;
import java.util.Set;

public class AssemblerGenerator {
	
	public static MsgStack getAssembler(Hashtable<String, ElementoTS> symbolTable, MsgStack code) {
		MsgStack asm = new MsgStack();
		asm.addMsg(".386");
		asm.addMsg(".model flat, stdcall");
		asm.addMsg("option casemap :none");
		asm.addMsg("include \\masm32\\include\\windows.inc");
		asm.addMsg("include \\masm32\\include\\kernel32.inc");
		asm.addMsg("include \\masm32\\include\\user32.inc");
		asm.addMsg("includelib \\masm32\\lib\\kernel32.lib");
		asm.addMsg("includelib \\masm32\\lib\\user32.lib");
		asm.addMsg(".data");
		asm.addAll(getDataAssembler(symbolTable));
		asm.addMsg(".code");
		asm.addMsg("start:");
		asm.addAll(code);
		asm.addAll(getExtraFunctions());
		asm.addMsg("end start");
		return asm;
	}
	
	private static MsgStack getDataAssembler(Hashtable<String, ElementoTS> symbolTable) {
		MsgStack data = new MsgStack();
		Set<String> keys = symbolTable.keySet();
		data.addMsg(".data");
		for(String key: keys){
			ElementoTS element = symbolTable.get(key);
			if (element.getEstructuraID().equals(ElementoTS.ID)) { 
				String word = "";
				// DW 16 bits para int - DD 32 bits para long
				if (element.getTipoAtributo().equals(ElementoTS.INT))
					word = "dw";
				if (element.getTipoAtributo().equals(ElementoTS.LONG))
					word = "dd";
				if (element.getTipoToken().equals(ElementoTS.VAR)) // Si es variable 
					data.addMsg( "_" + key + " " + word + " ?");
				else { //Sino es una colección
					String cadElements = element.getElemsCollection();
					cadElements = cadElements.replace('_', '?');
					data.addMsg( "_" + key + " " + word + " " + element.getCSize() + " dup " + cadElements);
				}				
			}
			if (element.getTipoToken().equals(ElementoTS.CAD)) {
				data.addMsg("_@cad" + element.getId() + " db \"" + key + "\"" + ", 0" );
			}			
		}
		return data;
	}
	
	private static MsgStack getExtraFunctions() {
		MsgStack extra = new MsgStack();
		//TODO: Agregar funciones de control dinámico
//		extra.addMsg("_");
		extra.addMsg("_print:"); //El print debe tener el puntero al mensaje en eax, debe pasarse como: lea eax, NombreCadena
		extra.addMsg("invoke MessageBox, NULL, eax, eax, MB_OK");
		extra.addMsg("invoke ExitProcess, 0");
		return extra;
	}
}
