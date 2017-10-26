package Parser.ContextFreeGrammar;

/** ����ʽ*/
public class Production {
	
	public static final int LAST_TERMINAL_PRECEDENCE = TerminalSymbol.NO_PRECEDENCE-1;

	/** ����ʽ��Production���ǹ��ɣ�
	 *  index����ţ��� 
	 *  	NonTerminalSymbol����������� �� Expression���Ҳ�������
	 *  */
	private NonTerminalSymbol lhs;//Left Hand Side�������
	private Expression rhs;//Right Hand Side�Ҳ�����
	private int index;//���
	private int precedence;//����Ȩ
	private SemanticAction action;//���嶯��
	
	/** ���캯����һ��*/
	Production(NonTerminalSymbol lhs, Expression rhs, int index) {
		this(lhs, rhs, LAST_TERMINAL_PRECEDENCE, null, index);
	}

	/** ���캯��������*/
	Production(NonTerminalSymbol lhs, Expression rhs, int precedence,
			SemanticAction action, int index) {
		this.lhs = lhs;
		this.rhs = rhs;
		if (precedence == LAST_TERMINAL_PRECEDENCE) {
			ExpressionIterator it = rhs.iterator(true);
			if (it.hasPrevTerminal()) {
				this.precedence = it.prevTerminal().getPrecedence();
			} else {
				this.precedence = TerminalSymbol.NO_PRECEDENCE;
			}
		} else {
			this.precedence = precedence;
		}
		this.action = action;
		this.index = index;
	}
	
	/** ��ȡ�������*/
	public NonTerminalSymbol getLHS() {
		return lhs;
	}

	/** ��ȡ�Ҳ�����*/
	public Expression getRHS() {
		return rhs;
	}
	
	/** ��ȡ����Ȩ*/
	public int getPrecedence() {
		return precedence;
	}
	
	/** ��ȡ���嶯��*/
	public SemanticAction getSemanticAction() {
		return action;
	}

	/** �������嶯��*/
	void setSemanticAction(SemanticAction action) {
		this.action = action;
	}
	
	/** ��ȡ���*/
	public int getIndex() {
		return index;
	}

	/** �������*/
	void setIndex(int index) {
		this.index = index;
	}

	public boolean equals(Object o) {
		return (o instanceof Production &&
			lhs.equals(((Production)o).lhs) &&
			rhs.equals(((Production)o).rhs) &&
			index == ((Production)o).index);
	}

	public int hashCode() {
		return index;
	}
	
	public String toString() {
		return lhs+" �� "+rhs;
	}

}
