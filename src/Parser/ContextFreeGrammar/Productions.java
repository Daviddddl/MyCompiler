package Parser.ContextFreeGrammar;

import java.util.*;

public class Productions {
	
	/** ��ű�ʶ�Ĳ���ʽ��*/
	private List<Production> prodByIndex = new ArrayList<Production>();	
	/** ������������ս������ʶ�Ĳ���ʽ��*/
	private Map<NonTerminalSymbol, LinkedList<Production>> prodByLHS = 
		new LinkedHashMap<NonTerminalSymbol, LinkedList<Production>>();	
	/** �Ҳ����������ʽ����ʶ�Ĳ���ʽ��*/
	private Map<Expression, LinkedList<Production>> prodByRHS = 
		new HashMap<Expression, LinkedList<Production>>();

	
	/** ��Ӳ���ʽ��һ��*/
	public Production addNew(NonTerminalSymbol lhs, Expression rhs,
			int precedence, SemanticAction action) {
		Production prod = new Production(lhs, rhs, precedence, action, count());
		prod = addToMaps(prod);
		prodByIndex.add(prod);
		return prod;
	}
	
	/** ��Ӳ���ʽ�����������������ʽ��ӵ���Maps������ӵ�List*/
	private Production addToMaps(Production prod) {
		NonTerminalSymbol lhs = prod.getLHS();
		Expression rhs = prod.getRHS();
		
		//��ȡ���������LHS����ʶ�Ĳ���ʽ��
		LinkedList<Production>  right;
		if (prodByLHS.containsKey(lhs)) {
			right = prodByLHS.get(lhs);
		} else {
			right = new LinkedList<Production> ();
		}		
		//�����ظ�
		Iterator<Production>  it = right.iterator();
		while (it.hasNext()) {
			Production oldProd = it.next();
			if (lhs.equals(oldProd.getLHS()) && rhs.equals(oldProd.getRHS())) {
				return oldProd;//�ظ��������ظ����ʽ
			}
		}
		//û���ظ�����Ӹò���ʽ���������������
		right.add(prod);
		prodByLHS.put(lhs, right);
		
		//��ȡ�Ҳ�������RHS����ʶ�Ĳ���ʽ��
		LinkedList<Production>  left;
		if (prodByRHS.containsKey(rhs)) {
			left = prodByRHS.get(rhs);
		} else {
			left = new LinkedList<Production> ();
		}
		//����Ҫ�ڲ����ظ��ˣ��Ѿ�������һ������¼�����
		left.add(prod);
		prodByRHS.put(rhs, left);
		return prod;
	}

	/** ��Ӳ���ʽ������*/
	public Production addNew(NonTerminalSymbol lhs, Expression rhs, int precedence) {
		return addNew(lhs, rhs, precedence, null);
	}

	/** ��Ӳ���ʽ���ģ�*/
	public Production addNew(NonTerminalSymbol lhs, Expression rhs, SemanticAction action) {
		return addNew(lhs, rhs, Production.LAST_TERMINAL_PRECEDENCE, action);		
	}

	/** ��Ӳ���ʽ���壩*/
	public Production addNew(NonTerminalSymbol lhs, Expression rhs) {
		return addNew(lhs, rhs, Production.LAST_TERMINAL_PRECEDENCE, null);
	}

	/** ��Ӳ���ʽ����.1��*/
	public void addNew(NonTerminalSymbol lhs, List<Expression> rhsList,
			int precedence, SemanticAction action) {
		Iterator<Expression> it = rhsList.iterator();
		while (it.hasNext()) {
			Expression rhs = (Expression)it.next();
			addNew(lhs, rhs, precedence, action);
		}
	}

	/** ��Ӳ���ʽ����.2��*/
	public void addNew(NonTerminalSymbol lhs, List<Expression> rhsList, int precedence) {
		addNew(lhs, rhsList, precedence, null);
	}

	/** ��Ӳ���ʽ����.3��*/
	public void addNew(NonTerminalSymbol lhs, List<Expression> rhsList, SemanticAction action) {
		addNew(lhs, rhsList, Production.LAST_TERMINAL_PRECEDENCE, action);
	}

	/** ��Ӳ���ʽ����.4��*/
	public void addNew(NonTerminalSymbol lhs, List<Expression> rhsList) {
		addNew(lhs, rhsList, Production.LAST_TERMINAL_PRECEDENCE, null);
	}
	
	/** ���ز���ʽ��Ŀ*/
	public int count() {
		return prodByIndex.size();
	}

	/** ��ȡ������*/
	public Iterator<Production> iterator() {
		return Collections.unmodifiableList(prodByIndex).iterator();//����ָ���б�Ĳ����޸���ͼ
	}

	public Iterator<Production> iterator(NonTerminalSymbol a) {
		List<Production> prodList = find(a);
		if (prodList == null) {
			// iterator over the empty list
			return new Iterator<Production>() {
				public boolean hasNext() { return false; }
				public Production next() { throw new NoSuchElementException(); }
				public void remove() { throw new UnsupportedOperationException(); }
			};
		} else {
			return Collections.unmodifiableList(prodList).iterator();
		}
	}

	public Production find(int index) {
		if (index < 0 || index >= count()) {
			return null;
		} else {
			return (Production)prodByIndex.get(index);
		}
	}

	/** ��ѯ���ʽ��һ��*/
	public LinkedList<Production> find(NonTerminalSymbol a) {
		return prodByLHS.get(a);
	}

	/** ��ѯ���ʽ��һ��*/
	public LinkedList<Production> find(Expression w) {
		return prodByRHS.get(w);
	}

	/** ֻ���Maps�������List*/
	private void clearMaps() {
		prodByLHS.clear();
		prodByRHS.clear();
	}

	/** ��ղ���ʽ��*/
	public void clear() {
		prodByIndex.clear();
		clearMaps();
	}

	/** ���Maps����List*/
	void removeUseless(List<NonTerminalSymbol> uselessVar) {
		
		clearMaps();
		
		List<Production> tmp = new ArrayList<Production>();//���ڱ������õĲ���ʽ
		for (int i = 0; i<prodByIndex.size(); i++) {
			Production prod = find(i);
			boolean useful = true;
			
			//�����������ʽ�Ƿ�������ս��
			if (uselessVar.contains(prod.getLHS())) {//������������һ���ս����
				useful = false;				
			} 
			else {//����Ҳ�����
				Expression w = prod.getRHS();//��ȡ���ò���ʽ���Ҳ�������һ�����ʽ��
				ExpressionIterator itW = w.iterator();
				while (itW.hasNextNonTerminal()) {
					NonTerminalSymbol a = itW.nextNonTerminal();
					if (uselessVar.contains(a)) {
						useful = false;
						break;
					}
				}
			}			
			if (useful) {//������õĲ���ʽ��TMP
				prod.setIndex(tmp.size()); 
				tmp.add(prod);
				addToMaps(prod);
			}
		}
		prodByIndex = tmp;//ָ�����ñ��ʽ
	}

	/** �ж��Ƿ����*/
	public boolean isInvertible() {
		Iterator<Expression> it = prodByRHS.keySet().iterator();
		while (it.hasNext()) {
			List<Production> rhsList = find((Expression)it.next());
			switch (rhsList.size()) {
				case 0: throw new RuntimeException("Null value in prodByRHS");
				case 1: break;
				default: return false;
			}
		}
		return true;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Set<NonTerminalSymbol> keySet = prodByLHS.keySet();
		Iterator<NonTerminalSymbol> it = keySet.iterator();
		while (it.hasNext()) {
			NonTerminalSymbol var = (NonTerminalSymbol)it.next();
			sb.append(var+" �� ");
			List<Production> rhsList = prodByLHS.get(var);
			Iterator<Production> itL = rhsList.iterator();
			while (itL.hasNext()) {
				sb.append(((Production)itL.next()).getRHS());
				if (itL.hasNext()) {
					sb.append(" | ");
				}
			}
			sb.append(";\n");
		}
		return sb.toString();
	}
}
