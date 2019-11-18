package utils;

import java.util.Hashtable;
import java.util.Set;

public class DataGenerator {
	public static MsgStack getDataAseembler(Hashtable<String, ElementoTS> symbolTable) {
		MsgStack data = new MsgStack();
		Set<String> keys = symbolTable.keySet();
		data.addMsg(".DATA");
		for(String key: keys){
			ElementoTS element = symbolTable.get(key);
			if (element.getEstructuraID().equals(ElementoTS.ID)) { 
				String word = "";
				// DW 16 bits para int - DD 32 bits para long
				if (element.getTipoAtributo().equals(ElementoTS.INT))
					word = "DW";
				if (element.getTipoAtributo().equals(ElementoTS.LONG))
					word = "DD";
				if (element.getTipoToken().equals(ElementoTS.VAR)) // Si es variable 
					data.addMsg( "_" + key + " " + word + " ?");
				else { //Sino es una colección
					String cadElements = element.getElemsCollection();
					cadElements = cadElements.replace('_', '?');
					data.addMsg( "_" + key + " " + word + " " + element.getCSize() + " DUP " + cadElements);
				}				
			}
			if (element.getTipoToken().equals(ElementoTS.CAD)) {
				data.addMsg("_@cad" + element.getId() + " DB \"" + key + "\"" + ", 0" );
			}			
		}
		return data;
	}
}
