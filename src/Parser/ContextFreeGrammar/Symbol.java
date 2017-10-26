package Parser.ContextFreeGrammar;

/** �ս����terminal symbol���ͷ��ս����non-terminals symbol���ĸ���*/
public abstract class Symbol {
	
	protected String name;
	protected int index;//����ɾ�����÷��ս��
	protected String type;

	/** ���캯����ȱʡ��*/
	public Symbol(){
	}
	
	/** ���캯����һ��*/
	public Symbol(String name, int index, String type) {
		this.name = name;
		this.index = index;
		this.type = type;
	}

	/** ���캯��������*/
	public Symbol(String name, int index) {
		this(name, index, null);
	}
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	/** ����ɾ�����÷��ս��*/
	void setIndex(int index) {
		this.index = index;
	}

	public boolean equals(Object o) {
		return (o instanceof Symbol 
				&& name.equals(((Symbol)o).name)
				&& index == ((Symbol)o).index);
	}

	public int hashCode() {
		return isTerminal()? -index: +index;
	}

	public String toString() {
		return getName();
	}

	public abstract boolean isTerminal();
}
