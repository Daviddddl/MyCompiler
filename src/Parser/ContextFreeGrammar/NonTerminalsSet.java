package Parser.ContextFreeGrammar;

import java.util.*;

/** ���ս��������HashMap��ArrayList���*/
public class NonTerminalsSet {
	private Map<String, NonTerminalSymbol> varByName = new HashMap<String, NonTerminalSymbol>();
	private List<NonTerminalSymbol> varByIndex = new ArrayList<NonTerminalSymbol>();

	/** ���캯����ȱʡ��*/
	public NonTerminalsSet(){}
	/** ���Symbol��һ��*/
	public NonTerminalSymbol addNew(String name, String type) {
		NonTerminalSymbol var = new NonTerminalSymbol(name, count(), type);
		if (varByName.containsKey(name)) {
			throw new RuntimeException("�ظ����ս�� ("+name+")");
		}
		varByName.put(name, var);
		varByIndex.add(var);
		return var;
	}

	/** ���Symbol������*/
	public NonTerminalSymbol addNew(String name) {
		return addNew(name, null);
	}
	
	/** ��ȡSymbol����*/
	public int count() {
		return varByIndex.size();
	}

	/** ��ȡ������*/
	public Iterator<NonTerminalSymbol> iterator() {
		return Collections.unmodifiableList(varByIndex).iterator();
	}

	/** ��ȡSymbol��һ��*/
	public NonTerminalSymbol find(String name) {
		return (NonTerminalSymbol)varByName.get(name);
	}

	/** ��ȡSymbol������*/
	public NonTerminalSymbol find(int index) {
		if (index < 0 || index >= count()) {
			return null;
		} else {
			return (NonTerminalSymbol)varByIndex.get(index);
		}
	}

	/** ��շ��ս����*/
	public void clear() {
		varByName.clear();
		varByIndex.clear();
	}
	
	/** ���varByNam�����÷��ս��s */
	void removeUseless(List<NonTerminalSymbol> s) {
		
		//ɾ��varByNam�����÷��ս��s
		Iterator<NonTerminalSymbol> it = s.iterator();
		while (it.hasNext()) {
			NonTerminalSymbol a = (NonTerminalSymbol)it.next();
			varByName.remove(a.getName());
		}
		
		//��������ÿ��NonTerminalSymbol��index
		List<NonTerminalSymbol> tmp = new ArrayList<NonTerminalSymbol>();
		for (int i = 0; i<varByIndex.size(); i++) {
			NonTerminalSymbol a = (NonTerminalSymbol)varByIndex.get(i);
			if (!s.contains(a)) {
				a.setIndex(tmp.size()); 
				tmp.add(a);
			}
		}
		varByIndex = tmp;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator<NonTerminalSymbol> it = iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	public Map<String, NonTerminalSymbol> getVarByName() {
		return varByName;
	}
	public void setVarByName(Map<String, NonTerminalSymbol> varByName) {
		this.varByName = varByName;
	}
	public List<NonTerminalSymbol> getVarByIndex() {
		return varByIndex;
	}
	public void setVarByIndex(List<NonTerminalSymbol> varByIndex) {
		this.varByIndex = varByIndex;
	}
}
