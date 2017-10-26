package Parser.LR1Parser;

/** LR(0)��Ŀ��*/
import Parser.ContextFreeGrammar.*;

public class LR0Item implements Cloneable {
	
	private Production p;//����ʽ
	private ExpressionIterator it;//���ʽ������

	/** ���캯����һ��*/
	public LR0Item(Production p, int position) {
		this.p = p;
		Expression w = p.getRHS();
		if (position >=0 && position<=w.size()) {
			it = w.iterator();
			while (position>0) {
				it.next();
				position--;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/** ���캯��������*/
	public LR0Item(Production p) {
		this(p, 0);
	}

	public boolean equals(Object o) {
		return (o instanceof LR0Item &&
			p.equals(((LR0Item)o).p) &&
			getPosition() == ((LR0Item)o).getPosition());
	}

	public int hashCode() {
		return 17+p.hashCode()*37+getPosition();
	}

	public Object clone() {
		try {
			LR0Item item = (LR0Item)super.clone();
			item.it = (ExpressionIterator)it.clone();
			return item;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public LR0Item nextItem() {
		LR0Item item = (LR0Item)clone();
		item.it.next();
		return item;
	}

	public boolean isComplete() {
		return !it.hasNext();
	}

	public boolean isNew() {
		return !it.hasPrev();
	}

	public Symbol getNextSymbol() {
		return it.getNext();
	}

	public Production getProduction() {
		return p;
	}

	public int getPosition() {
		return it.nextIndex();
	}

	public String toString() {
		return p.getLHS()+" �� "+it.prefix()+" . "+it.suffix();
	}

	Expression suffix() {
		return it.suffix();
	}

	public static void main(String args[]) {
		//�ս����
		TerminalsSet t = new TerminalsSet();
		TerminalSymbol a = t.addNew("a");
		TerminalSymbol b = t.addNew("b");
		TerminalSymbol c = t.addNew("c");
		//���ս����
		NonTerminalsSet v = new NonTerminalsSet();
		NonTerminalSymbol S = v.addNew("S");
		NonTerminalSymbol A = v.addNew("A");
		NonTerminalSymbol B = v.addNew("B");
		//���ʽ�������ʽ
		Expression w = new Expression();
		w.addLast(a); w.addLast(A); w.addLast(b); w.addLast(B); w.addLast(c);
		Productions p = new Productions();
		Production prod = p.addNew(S, w);
		//������Ŀ��
		LR0Item items[] = new LR0Item[w.size()+1];
		items[0] = new LR0Item(prod);
		//��ӡ��Ŀ��
		System.out.println("items[0]: "+items[0]);
		System.out.println();
		for (int i = 1; i<=w.size(); i++) {
			items[i] = items[i-1].nextItem();
			System.out.println("items["+i+"]: "+items[i]);
		}
		System.out.println();
		// in the end
		for (int i = 0; i<=w.size(); i++) {
			System.out.println("items["+i+"]: "+items[i]);
		}
	}
}
