package view;

import java.util.Hashtable;

import logic.Lexicon;
import logic.TuplaTS;
import utils.MsgStack;

public final class Compilator {

	private static Hashtable<String, TuplaTS> simbTable;
	private static MsgStack msgStack;
	private static Lexicon lexico;
	
	public Compilator()
	{

	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Compilator compi= new Compilator();
		compi.lexico= new Lexicon(args[0], compi.msgStack, compi.simbTable);
		
	}

}
