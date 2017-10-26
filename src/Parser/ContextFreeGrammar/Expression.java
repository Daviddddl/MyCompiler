package Parser.ContextFreeGrammar;

import java.util.*;

/** ��Symbol���ɵ�˫����*/
public class Expression {
	private Node first;
	private Node last;
	private int size;
	
	/** ������Symbol���ɵ�˫�����ڵ���Node*/
	private class Node {
		Symbol sym;
		Node prev;//ǰ��ָ��
		Node next;//����ָ��

		public Node() {
			this(null, null, null);
		}
		
		public Node(Symbol sym, Node prev, Node next) {
			this.sym = sym;
			this.prev = prev;
			this.next = next;
		}

	}
	
	private class WordIteratorP implements ExpressionIterator {
		/** �����ᶨ�巽��*/
		private final int LEFT = -1;
		private final int NONE = 0;
		private final int RIGHT = +1;
		private Node p;//
		private Node n;//��һ��ָ��next
		private int direction;
		private int index;
		
		public WordIteratorP(boolean end) {
			direction = NONE;
			if (!end) {
				p = null;
				n = first;
				index = 0;
			} else {
				p = last;
				n = null;
				index = Expression.this.size();
			}
		}

		public WordIteratorP() {
			this(false);
		}

		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}

		/** �ж���һ����һ����true��ʾ��һ��Symbol����*/
		public boolean hasNext() {
			return n != null;
		}

		/** �ж���һ����������true��ʾ��һ��Terminal����*/
		public boolean hasNextTerminal() {
			Node q = n;
			while (q != null) {
				if (q.sym.isTerminal()) {
					return true;
				}
				q = q.next;
			}
			return false;
		}

		/** �ж���һ����������true��ʾ��һ��NonTerminal����*/
		public boolean hasNextNonTerminal() {
			Node q = n;
			while (q != null) {
				if (!q.sym.isTerminal()) {
					return true;
				}
				q = q.next;
			}
			return false;
		}

		/** ��ȡ��һ����һ����return��һ��Symbol*/
		public Symbol getNext() {
			return n.sym;
		}
		
		/** ��ȡ��һ����������return��һ��Terminal*/
		public TerminalSymbol nextTerminal() {
			direction = RIGHT;
			while (true) {
				index++;
				p = n;
				n = n.next;
				if (p.sym.isTerminal()) {
					return (TerminalSymbol)p.sym;
				}
			}
		}

		/** ��ȡ��һ����������return��һ��NonTerminal*/
		public NonTerminalSymbol nextNonTerminal() {
			direction = RIGHT;
			while (true) {
				index++;
				p = n;
				n = n.next;
				if (!p.sym.isTerminal()) {
					return (NonTerminalSymbol)p.sym;
				}
			}
		}

		/** ָ��p��n�������һλ*/
		public Symbol next() {
			direction = RIGHT;
			index++;
			p = n;
			n = n.next;
			return p.sym;
		}

		/** ��ȡ��һ��index*/
 		public int nextIndex() {
 			return index;
 		}

 		/** �ж���һ����һ����true��ʾ��һ��Symbol����*/
		public boolean hasPrev() {
			return p != null;
		}

		/** �ж���һ����������true��ʾ��һ��Terminal����*/
		public boolean hasPrevTerminal() {
			Node q = p;
			while (q != null) {
				if (q.sym.isTerminal()) {
					return true;
				}
				q = q.prev;
			}
			return false;
		}

		/** �ж���һ����������true��ʾ��һ��NonTerminal����*/
		public boolean hasPrevNonTerminal() {
			Node q = p;
			while (q != null) {
				if (!q.sym.isTerminal()) {
					return true;
				}
				q = q.prev;
			}
			return false;
		}

		/** ��ȡ��һ����һ����return��һ��Symbol*/
		public Symbol getPrev() {
			return p.sym;
		}

		/** ��ȡ��һ����������return��һ��Terminal*/
		public TerminalSymbol prevTerminal() {
			direction = LEFT;
			while (true) {
				index--;
				n = p;
				p = p.prev;
				if (n.sym.isTerminal()) {
					return (TerminalSymbol)n.sym;
				}
			}
		}

		/** ��ȡ��һ����������return��һ��NonTerminal*/
		public NonTerminalSymbol prevNonTerminal() {
			direction = LEFT;
			while (true) {
				index--;
				n = p;
				p = p.prev;
				if (!n.sym.isTerminal()) {
					return (NonTerminalSymbol)n.sym;
				}
			}
		}

		/** ָ��p��n����ǰ��һλ*/
		public Symbol prev() {
			direction = LEFT;
			index--;
			n = p;
			p = p.prev;
			return n.sym;
		}

		/** ��ȡ��һ��index*/
 		public int prevIndex() {
 			return index-1;
 		}

		/** ��ȡ��ǰ����������нڵ�*/
		public Expression suffix() {
			Expression w = new Expression();
			Node q = n;
			while (q != null) {
				w.addLast(q.sym);
				q = q.next;
			}
			if(w.size()==0) return new Expression(
					new Node(new TerminalSymbol(" ", 0), null, null),
					new Node(new TerminalSymbol(" ", 0), null, null), 
					1);//����һ�ո�
			else return w;
		}

		/** ��ȡ��ǰ�ڵ�ǰ������нڵ�*/
		public Expression prefix() {
			Expression w = new Expression();
			Node q = p;
			while (q != null) {
				w.addFirst(q.sym);
				q = q.prev;
			}
			if(w.size()==0) return new Expression(
					new Node(new TerminalSymbol(" ", 0), null, null),
					new Node(new TerminalSymbol(" ", 0), null, null), 
					1);//����һ�ո�
			else return w;
		}
		
 		/** �Ƴ�һ�����*/
		public void remove() {
			if (direction == LEFT) {
				Expression.this.remove(n);
			} else if (direction == RIGHT) {
				Expression.this.remove(p);
				index--;
			} else {
				throw new IllegalStateException();
			}
		}

		/** �޸�һ�����*/
		public void set(Symbol sym) {
			if (direction == LEFT) {
				n.sym = sym;
			} else if (direction == RIGHT) {
				p.sym = sym;
			} else {
				throw new IllegalStateException();
			}
		}
		
		/** β�����һ�����*/
		public void addBefore(Symbol sym) {
			if (n == null) {
				addLast(sym);
				p = last;
			} else {
				addBeforeNode(n, sym);
				p = n.prev;
			}
			index++;
		}

		/** �ײ����һ�����*/
		public void addAfter(Symbol sym) {
			if (p == null) {
				addFirst(sym);
				n = first;
			} else {
				addAfterNode(p, sym);
				n = p.next;
			}
		}

		/** β�����һ�����ʽ*/
		public void addWordBefore(Expression w) {
			if (!w.isEmpty()) {
				if (n == null) {
					addLast(w);
					p = last;
				} else {
					addBeforeNode(n, w);
					p = n.prev;
				}
				index += w.size();
			}
		}

		/** �ײ����һ�����ʽ*/
		public void addWordAfter(Expression w) {
			if (!w.isEmpty()) {
				if (p == null) {
					addFirst(w);
					n = first;
				} else {
					addAfterNode(p, w);
					n = p.next;
				}
			}
		}

	}

	/** ���캯��(private) */
	private Expression(Node first, Node last, int size) {
		this.first = first;
		this.last = last;
		this.size = size;
	}

	/** �����ձ��ʽ(��/�� labmda/epsilon) */
	public Expression() {
		this(null, null, 0);
	}

	/** ����ĳ���ʽ*/
	public Expression(Expression w) {
		this();
		addLast(w);
	}

	/** ��symbol���ɱ��ʽ��һ��*/
	public Expression(Symbol sym) {
		this();
		addLast(sym);
//		this.first = this.last = new Node(sym, null, null);
//		this.size = 1;
	}

	/** ��symbol���ɱ��ʽ������*/
	public Expression(Symbol sym1, Symbol sym2) {
		this(sym1);
		addLast(sym2);
	}

	/** ��symbol���ɱ��ʽ������
	 *  �±��ʽ��˳�����symbol����˳��*/
	public Expression(Collection<Symbol> c) {
		this(null, null, 0);
		Iterator<Symbol> it = c.iterator();
		while (it.hasNext()) {
			addLast((Symbol)it.next());
		}
	}

	/** ��ȡp��q֮���ӱ��ʽ������p��q���ڵ㣩*/
	private Expression subWord(Node p, Node q) {
		int s = 1;
		Node it = p;
		while (it != q) {
			it = it.next;
			s++;
		}
		return new Expression(p, q, s);
	}

	/** ��ȡ���ʽ��symbol��Ŀ*/
	public int size() {
		return size;
	}

	/** true��ʾ�� */
	public boolean isEmpty() {
		return first == null;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Expression)) {
			return false;
		}
		Expression w = (Expression)o;
		if (w.size() != size()) {
			return false;
		}
		ExpressionIterator it1 = iterator();
		ExpressionIterator it2 = w.iterator();
		while (it1.hasNext()) {
			if (!it1.next().equals(it2.next())) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int code = size();
		ExpressionIterator it = iterator();
		while (it.hasNext()) {
			code = 37 * code + it.next().hashCode();
		}
		return code;
	}


	/** true��ʾword����ĳsymbol*/
	public boolean contains(Symbol sym) {
		Node p = first;
		while (p != null) {
			if (p.sym.equals(sym)) {
				return true;
			}
			p = p.next;
		}
		return false;
	}

	//ʵ��˫�����
	/** ��ȡ���������ӵ�һ��symbol��ʼ������*/
	public ExpressionIterator iterator() {
		return new WordIteratorP();
	}

	/** ��ȡ��������endΪfalse������iterator()��һ��symbol��ʼ��������
	 *  endΪtrue�������һ��symbol��ʼ��ǰ����*/
	public ExpressionIterator iterator(boolean end) {
		return new WordIteratorP(end);
	}

	/** ��˫����ת��������*/
	public Symbol[] toArray() {
		Symbol[] arr = new Symbol[size];
		Node p = first;
		int i = 0;
		while (p != null) {
			arr[i++] = p.sym;
			p = p.next;
		}
		return arr;
	}

	/** ����ͷ���symbol */
	public void addFirst(Symbol sym) {
		Node p = new Node(sym, null, first);
		if (first == null) {
			first = last = p;
		} else {
			first.prev = p;
			first = p;
		}
		size++;
	}

	/** ��symbol���ӵ���δ */
	public void addLast(Symbol sym) {
		Node p = new Node(sym, last, null);
		if (last == null) {
			first = last = p;
		} else {
			last.next = p;
			last = p;
		}
		size++;
	}

	/** ��ĳNodeǰ���symbol*/
	private void addBeforeNode(Node n, Symbol sym) {
		if (n == first) {
			addFirst(sym);
		} else {
			Node p = n.prev;
			Node q = new Node(sym, p, n);
			p.next = q;
			n.prev = q;
			size++;
		}
	}

	/** ��ĳNode�����symbol*/
	private void addAfterNode(Node p, Symbol sym) {
		if (p == last) {
			addLast(sym);
		} else {
			Node n = p.next;
			Node q = new Node(sym, p, n);
			p.next = q;
			n.prev = q;
			size++;
		}
	}

	/** ���أ�����ͷ���word*/
	public void addFirst(Expression w) {
		if (!w.isEmpty()) {
			Expression temp = new Expression(w);
			if (first != null) {
				first.prev = temp.last;
			}
			if (temp.last != null) {
				temp.last.next = first;
				first = temp.first;
			}
			size += temp.size;
		}
	}

	/** ���أ�����δ���word */
	public void addLast(Expression w) {
		if (!w.isEmpty()) {
			ExpressionIterator it = w.iterator();
			while (it.hasNext()) {
				addLast(it.next());
			}
		}
	}

	/** ���أ���ĳNodeǰ���word*/
	private void addBeforeNode(Node n, Expression w) {
		if (!w.isEmpty()) {
			if (n == first) {
				addFirst(w);
			} else {
				Node p = n.prev;
				Expression temp = new Expression(w);
				temp.first.prev = p;
				temp.last.next = n;
				p.next = temp.first;
				n.prev = temp.last;
				size += temp.size;
			}
		}
	}

	/** ���أ���ĳNode�����word */
	private void addAfterNode(Node p, Expression w) {
		if (!w.isEmpty()) {
			if (p == last) {
				addLast(w);
			} else {
				Node n = p.next;
				Expression temp = new Expression(w);
				temp.first.prev = p;
				temp.last.next = n;
				p.next = temp.first;
				n.prev = temp.last;
				size += temp.size;
			}
		}
	}

	/** ��ȡͷ����ʶ��*/
	public Symbol getFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return first.sym;
		}
	}

	/** ��ȡβ�ڵ��ʶ��*/
	public Symbol getLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return last.sym;
		}
	}

	/** �Ƴ��ڵ�*/
	private void remove(Node p) {
		if (p.prev == null) {
			first = p.next;
		} else {
			p.prev.next = p.next;
		}
		if (p.next == null) {
			last = p.prev;
		} else {
			p.next.prev = p.prev;
		}
		size--;
	}

	/** �Ƴ�ͷ�ڵ�*/
	public Symbol removeFirst() {
		if (first == null) {
			throw new NoSuchElementException();
		} else {
			Symbol ret = first.sym;
			if (first.next == null) {
				first = last = null;
				size = 0;
			} else {
				first.next.prev = null;
				first = first.next;
				size--;
			}
			return ret;
		}
	}

	/** �Ƴ�β�ڵ�*/
	public Symbol removeLast() {
		if (last == null) {
			throw new NoSuchElementException();
		} else {
			Symbol ret = last.sym;
			if (last.prev == null) {
				first = last = null;
				size = 0;
			} else {
				last.prev.next = null;
				last = last.prev;
				size--;
			}
			return ret;
		}
	}

	/** �Ƴ����ʽ�е�һ�����ֵ�ĳ��ʶ��*/
	public boolean remove(Symbol sym) {
		Node p = first;
		while (p != null) {
			if (p.sym.equals(sym)) {
				remove(p);
				return true;
			}
			p = p.next;
		}
		return false;
	}

	/** ��ձ��ʽ*/
	public void clear() {
		size = 0;
		first = last = null;
	}

	/** ��ȡ���ʽ�ľ���*/
	public Expression mirror() {
		Expression w = new Expression();
		ExpressionIterator it = iterator();
		while (it.hasNext()) {
			w.addFirst(it.next());
		}
		return w;
	}

	public String toString() {
		if(size()==0)	return "��";
		else{
			StringBuffer sb = new StringBuffer();
			ExpressionIterator it = iterator();
			while (it.hasNext()) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append(" ");
				}
			}
			return sb.toString();
		}
	}


	public static void main(String args[]) {
		List<Symbol> symbolList = new LinkedList<Symbol>();
		NonTerminalsSet v = new NonTerminalsSet();
		TerminalsSet t = new TerminalsSet();

		symbolList.add(t.addNew("LParameters"));
		symbolList.add(v.addNew("exp1"));
		symbolList.add(t.addNew("+"));
		symbolList.add(v.addNew("exp2"));
		symbolList.add(t.addNew("RParameters"));
		Expression w = new Expression(symbolList);
		
		System.out.println("���ʽ�� "+w);
		System.out.println();

		// ��������Terminals
		System.out.print("Terminals only: {");
		ExpressionIterator it = w.iterator();
		while (it.hasNextTerminal()) {
			Symbol sym = it.nextTerminal();
			System.out.print(sym+" ");
		}
		System.out.println("}");
		System.out.println();

		// ��������Terminals
		System.out.print("Terminals backwards: {");
		while (it.hasPrevTerminal()) {
			Symbol sym = it.prevTerminal();
			System.out.print(sym+" ");
		}
		System.out.println("}");
		System.out.println();


		// ��������Non-Terminals
		System.out.print("��������NonTerminals only: {");
		it = w.iterator();
		while (it.hasNextNonTerminal()) {
			Symbol sym = it.nextNonTerminal();
			System.out.print(sym+" ");
		}
		System.out.println("}");
		System.out.println();

		// ��������Non-Terminals
		System.out.print("��������NonTerminals backwards: {");
		while (it.hasPrevNonTerminal()) {
			Symbol sym = it.prevNonTerminal();
			System.out.print(sym+" ");
		}
		System.out.println("}");
		System.out.println();

		Symbol[] s = w.toArray();
		System.out.print("Listת��Array: {");
		for (int i = 0; i<s.length; i++) {
			System.out.print(s[i]+" ");
		}
		System.out.println("}");
		System.out.println();

		System.out.println("���ʽ����\""+t.find(1).name+"\": "+w.contains(t.find(1)));
		System.out.println("���ʽ����\""+"EOF"+"\": "+w.contains(t.find("EOF")));
	}
}
