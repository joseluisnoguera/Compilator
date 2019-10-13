package logic;

import java.util.Hashtable;

import utils.MsgStack;
import utils.ElementoTS;

public final class Compilator {

	private static Hashtable<String, ElementoTS> simbTable;
	private static MsgStack msgStack;
	private static Lexicon lexico;
	private 
	
	public Compilator()
	{

	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Compilator compi= new Compilator();
		compi.lexico= new Lexicon(args[0], compi.msgStack, compi.simbTable);
		Parser sintactic = new Parser();
	}

}
