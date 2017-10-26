package Parser.ContextFreeGrammar;

import java.util.*;

/** �ս��������HashMap��ArrayList���*/
public class TerminalsSet {
	
	private Map<String,TerminalSymbol> termByName = new HashMap<String,TerminalSymbol>();
	private List<TerminalSymbol> termByIndex = new ArrayList<TerminalSymbol>();

	/** ���������*/
	public final TerminalSymbol EOF = find("EOF")!=null? find("EOF"): addNew("EOF");

	public TerminalsSet(){}
	/** רΪ����������ƵĹ��캯��*/
	public TerminalsSet(String strToAnalyze){
//		System.out.println(strToAnalyze);
		for(int i=0; i < strToAnalyze.length(); i++){
			String name = strToAnalyze.substring(i, i+1).trim();//subStirng��i��ָchar������j��ָchar
//			System.out.println(name);
//			termByName.put(name, term);
			if(!name.equals("#")){
				TerminalSymbol term = new TerminalSymbol(name, count(), null);
				termByIndex.add(i, term);
			}
//			addNew(temp);
		}
	}
	
	/** ���Symbol��һ��*/
	public TerminalSymbol addNew(String name, String type) {
		TerminalSymbol term = new TerminalSymbol(name, count(), type);
		if (termByName.containsKey(name)) {
			throw new RuntimeException("Duplicate Terminal ("+name+")");
		}
		termByName.put(name, term);
		termByIndex.add(term);
		return term;
	}

	/** ���Symbol������*/
	public TerminalSymbol addNew(String name) {
		return addNew(name, null);
	}

	/** ��ȡSymbol����*/
	public int count() {
		return termByIndex.size();
	}

	/** ��ȡ������*/
	public Iterator<TerminalSymbol> iterator() {
		return Collections.unmodifiableList(termByIndex).iterator();
	}

	/** ��ȡSymbol��һ��*/
	public TerminalSymbol find(String name) {
		return (TerminalSymbol)termByName.get(name);
	}

	/** ��ȡSymbol������*/
	public TerminalSymbol find(int index) {
		if (index < 0 || index >= count()) {
			return null;
		} else {
			return (TerminalSymbol)termByIndex.get(index);
		}
	}

	/** ����ս����*/
	public void clear() {
		termByName.clear();
		termByIndex.clear();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator<TerminalSymbol> it = iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	public Map<String, TerminalSymbol> getTermByName() {
		return termByName;
	}

	public void setTermByName(Map<String, TerminalSymbol> termByName) {
		this.termByName = termByName;
	}

	public List<TerminalSymbol> getTermByIndex() {
		return termByIndex;
	}

	public void setTermByIndex(List<TerminalSymbol> termByIndex) {
		this.termByIndex = termByIndex;
	}

	public TerminalSymbol getEOF() {
		return EOF;
	}
}
