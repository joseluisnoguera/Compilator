package utils;

import java.util.Vector;

public class MsgStack {
	private Vector<String> msgs;
	
	public MsgStack() {	msgs = new Vector<String>(); }
	
	public String getMsg(int index) { return (index <= msgs.size())? msgs.elementAt(index): null;	}

	public void addMsg(String msg) { msgs.add(msg); }
	
	public int getSize() { return msgs.size(); }
}
