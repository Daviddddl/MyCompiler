package Parser.ContextFreeGrammar;

/** ���ս��*/
public class NonTerminalSymbol extends Symbol {	
	
	/** ���캯����ȱʡ��*/
	public NonTerminalSymbol(){
		super();
	}
	/** ���캯����һ��*/
	NonTerminalSymbol(String name, int index, String type) {
		super(name, index, type);
	}

	/** ���캯��������*/
	NonTerminalSymbol(String name, int index) {
		this(name, index, null);
	}

	public boolean isTerminal() {
		return false;
	}
}
