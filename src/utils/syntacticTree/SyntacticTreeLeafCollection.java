package utils.syntacticTree;

public class SyntacticTreeLeafCollection extends SyntacticTreeLeaf {
	public SyntacticTreeLeafCollection(String lexema, SyntacticTree nodoIzq) {
		super(lexema);
		setHijoIzq(nodoIzq);
	}
}
