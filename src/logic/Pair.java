package logic;

import utils.SemanticAction;

public class Pair {
	private int state;
	private SemanticAction sAction;
	public Pair(int state, SemanticAction sAction)
	{
		this.state=state;
		this.sAction=sAction;
	}
	public int getState()
	{
		return this.state;
	}
	public SemanticAction getSemanticAction()
	{
		return this.sAction;
	}
}
