package Parser.ContextFreeGrammar;

import java.util.*;

/** �������޹��ķ���Context Free Grammar��*/
public class CFG {
	private NonTerminalsSet v;//���ս����
	private TerminalsSet t;//�ս����
	private NonTerminalSymbol s;//��ʼ��
	private Productions p;//����ʽ��

	
	private Set<NonTerminalSymbol> vNullable;//�ɿշ��ս���� 
	private Set<Production> pNullable;//�ɿղ���ʽ��
	
	private Set<Symbol>[] vFirst;//���ս��First������	
	private Set<Symbol>[] pFirst;//����ʽFirst������

	private Set<Symbol>[] vFollow;//���ս��Follow������

	/** ���캯����ȱʡ��*/
	public CFG(){}
	/** ���캯����һ��*/
	public CFG(String vStr, String tStr, String sStr, String pStr) {
		
		//�����ռ䣬���򣬱���
		v = new NonTerminalsSet();
		t = new TerminalsSet();
		
		//�������ս����
//		System.out.println(vStr+"   "+tStr);
		int i=0, j=vStr.indexOf(',');
		while(j!=-1){
			String temp = vStr.substring(i, j).trim();//subStirng��i��ָchar������j��ָchar
//			System.out.println(temp);
			v.addNew(temp);
			i = j+1;
			j = vStr.indexOf(',', i);
		}
		if(j==-1){
			String temp = vStr.substring(i, vStr.length()).trim();
//			System.out.println(temp);
			v.addNew(temp);
		}
		//�����ս����
		i=0; j=tStr.indexOf(',');
		while(j!=-1){
			String temp = tStr.substring(i, j).trim();
//			System.out.println(temp);
			t.addNew(temp);
			i = j+1;
			j = tStr.indexOf(',', i);
		}
		if(j==-1){
			String temp = tStr.substring(i, tStr.length()).trim();
//			System.out.println(temp);
			t.addNew(temp);
		}
		//������ʼ�ս��
		s = v.find(sStr.trim());
//		System.out.println(v.count() + " " + t.count());
//		System.out.println(s.index+" "+s.type+" "+s.name);
		//��������ʽ
		p = new Productions();
		i=0; j=pStr.indexOf(';');
		while(j!=-1){
			//����ʽ�ֳ� �������lhs��һ���ս���� �� �Ҳ�����rhs��һ���ʽ��
			String temp = pStr.substring(i, j);
			int m = temp.indexOf('��');
			NonTerminalSymbol lhs = v.find(temp.substring(0,m).trim());
			String tempRHS = temp.substring(m+1,temp.length()).trim();
//			System.out.println(lhs.name+"  "+tempRHS);
			Collection<Symbol> c = new LinkedList<Symbol>();
			for(int n=0; n<tempRHS.length(); n++){
				String tmpRHS = tempRHS.substring(n, n+1);
				if(v.getVarByName().containsKey(tmpRHS))
					c.add(v.find(tmpRHS));
				else if(t.getTermByName().containsKey(tmpRHS))
					c.add(t.find(tmpRHS));					
			}
			Expression rhs = new Expression(c);
			p.addNew(lhs, rhs);
			i = j+1;
			j = pStr.indexOf(';', i);
		}
		if(j==-1){
			//����ʽ�ֳ� �������lhs��һ���ս���� �� �Ҳ�����rhs��һ���ʽ��
			String temp = pStr.substring(i, pStr.length());
			int m = temp.indexOf('��');
			NonTerminalSymbol lhs = v.find(temp.substring(0,m).trim());
			String tempRHS = temp.substring(m+1,temp.length()).trim();
//			System.out.println(lhs.name+"  "+tempRHS);
			Collection<Symbol> c = new LinkedList<Symbol>();
			for(int n=0; n<tempRHS.length(); n++){
				String tmpRHS = tempRHS.substring(n, n+1);
				if(v.getVarByName().containsKey(tmpRHS))
					c.add(v.find(tmpRHS));
				else if(t.getTermByName().containsKey(tmpRHS))
					c.add(t.find(tmpRHS));					
			}
			if(c.toString().equals("��")){
				Expression rhs = new Expression();
				p.addNew(lhs, rhs);				
			}
			else{
				Expression rhs = new Expression(c);
				p.addNew(lhs, rhs);
			}
			
		}
		System.out.println(p.count());
		
		removeUseless();
	}
	/** ���캯��������*/
	public CFG(NonTerminalsSet v, TerminalsSet t, NonTerminalSymbol s, Productions p) {
		this.v = v;
		this.t = t;
		if (s == null) {
			this.s = v.find(0);
		} else {
			this.s = s;
		}
		this.p = p;
		removeUseless();
	}

	public NonTerminalsSet getNonTerminals() {
		return v;
	}

	public TerminalsSet getTerminals() {
		return t;
	}

	public NonTerminalSymbol getStartSymbol() {
		return s;
	}

	public Productions getProductions() {
		return p;
	}
	
	/** �Ƴ�û���õķ��ս���ͱ��ʽ�������ɴ�ģ�*/
	private void removeUseless() {
		boolean haveUseless;
		do {
			Set<NonTerminalSymbol> vAccessible = new HashSet<NonTerminalSymbol>();
			vAccessible.add(s);//�����ʼ��
			LinkedList<NonTerminalSymbol> queue = new LinkedList<NonTerminalSymbol>();
			queue.addLast(s);

			while (!queue.isEmpty()) {
				NonTerminalSymbol a = (NonTerminalSymbol)queue.removeLast();
				Iterator<Production> itL = p.iterator(a);
				while (itL.hasNext()) {
					Expression w = ((Production)itL.next()).getRHS();
					ExpressionIterator itW = w.iterator();
					while (itW.hasNextNonTerminal()) {
						NonTerminalSymbol b = itW.nextNonTerminal();
						if (vAccessible.add(b)) {
							queue.addLast(b);
						}
					}
				}
			}

			Set<NonTerminalSymbol> vProductive = new HashSet<NonTerminalSymbol>();
			boolean changed;
			do {
				changed = false;
				Iterator<NonTerminalSymbol> itV = v.iterator();
				while (itV.hasNext()) {
					NonTerminalSymbol a = (NonTerminalSymbol)itV.next();
					if (!vProductive.contains(a)) {
						Iterator<Production> itP = p.iterator(a);
						while (itP.hasNext()) {
							Expression w = ((Production)itP.next()).getRHS();
							ExpressionIterator itW = w.iterator();
							boolean isProductive = true;
							while (itW.hasNextNonTerminal()) {
								NonTerminalSymbol b =
									(NonTerminalSymbol)itW.nextNonTerminal();
								if (!vProductive.contains(b)) {
									isProductive = false;
									break;
								}
							}
							if (isProductive) {
								vProductive.add(a);
								changed = true;
								break;
							}
						}
					}
				}
			} while (changed);

			haveUseless = vAccessible.size()<v.count() || vProductive.size()<v.count();
			if (haveUseless) {
				// ���ﲻʹ��Map��ʹ��LinkedList��Щ
				LinkedList<NonTerminalSymbol> vUseless = new LinkedList<NonTerminalSymbol>();
				for (int i = 0; i<v.count(); i++) {
					NonTerminalSymbol a = v.find(i);
					if (!vAccessible.contains(a) || !vProductive.contains(a)) {
						vUseless.add(a);
					}
				}
				v.removeUseless(vUseless);
				p.removeUseless(vUseless);
			}
		} while(haveUseless);
	}

	/** ���һ���µ���ʼ���ս�� $START�������ʽ $START �� ��ʼ��, EOF */
	public Production addStartProduction() {
		NonTerminalSymbol oldStart = s;
		s = v.addNew("$START");
		Expression rhs = new Expression();
		rhs.addLast(oldStart);
//		rhs.addLast(t.EOF);
		return p.addNew(s, rhs);
	}

	/** ��ʶ������*/
	public int symbolCount() {
		return v.count()+t.count();
	}

	
	public int getSID(Symbol x) {
		int xSID = x.getIndex();
		if (x.isTerminal()) {
			xSID += v.count();
		}
		return xSID;
	}

	public Symbol symbol(int SID) {
		if (0 <= SID && SID < v.count()) {
			return v.find(SID);
		} else if (SID < v.count()+t.count()) {
			return t.find(SID-v.count());
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** ����ɿռ�(�ɿշ��ս�������ɿղ���ʽ��)*/
	public void computeNullable() {
		vNullable = new HashSet<NonTerminalSymbol>();
		pNullable = new HashSet<Production>();
		Set<Production> pVisited = new HashSet<Production>();

		boolean changed;
		do {
			changed = false;
			Iterator<NonTerminalSymbol> itV = v.iterator();
			while (itV.hasNext()) {
				NonTerminalSymbol a = (NonTerminalSymbol)itV.next();
				Iterator<Production> itP = p.iterator(a);
				while (itP.hasNext()) {
					Production prod = (Production)itP.next();
					if (!pVisited.contains(prod)) {
						Expression w = prod.getRHS();
						ExpressionIterator itW = w.iterator();
						if (itW.hasNextTerminal()) {
							pVisited.add(prod);
						} else {
							boolean nullable = true;
							while (itW.hasNext() && nullable) {
								NonTerminalSymbol b = (NonTerminalSymbol)itW.next();
								if (!vNullable.contains(b)) {
									nullable = false; // cannot decide right now
								}
							}
							if (nullable) {
								vNullable.add(a);
								pNullable.add(prod);
								pVisited.add(prod);
								changed = true;
							}
						}
					}
				}
			}
		} while (changed);
	}

	/** �жϣ�true��ʾ���ս�������Ƴ��� */
	public boolean nullable(NonTerminalSymbol var) {
		return vNullable.contains(var);
	}

	/** �жϣ�true��ʾ�ò���ʽ�����Ƴ��� */
	public boolean nullable(Production prod) {
		return pNullable.contains(prod);
	}

	/** �жϣ�true��ʾ�ñ��ʽ�����Ƴ��� */
	public boolean nullable(Expression w) {
		ExpressionIterator itW = w.iterator();
		if (itW.hasNextTerminal()) {
			return false;
		}
		while (itW.hasNext()) {
			NonTerminalSymbol var = (NonTerminalSymbol)itW.next();
			if (!nullable(var)) {
				return false;
			}
		}
		return true;
	}
	
	
	/** ���������� */
	private void eliminateEpsilon() {
		if (vNullable == null)
			computeNullable();

		Productions pNew = new Productions();		
		Iterator<Production> itP = p.iterator();
		while (itP.hasNext()) {
			Production prod = (Production)itP.next();
			Expression w = prod.getRHS();
			// ���Ԧ���������
			if (!w.isEmpty()) {
				ArrayList<Expression> words = new ArrayList<Expression>();
				ExpressionIterator itW = w.iterator();
				while (itW.hasNextNonTerminal()) {
					NonTerminalSymbol x = itW.nextNonTerminal();
//					System.out.println("NonTerminal:"+x);
//					System.out.println("words:"+words);
//					System.out.println("w:"+w);

					if (vNullable.contains(x)) {
						Expression prefW = itW.prefix();
//						System.out.println("[NULABLE]");
//						System.out.println("prefW:"+prefW);
						if (words.isEmpty()) {
							Expression w1 = new Expression(prefW);
							Expression w0 = new Expression(prefW);
							w0.removeLast();
							words.add(w0);
							words.add(w1);

							w = new Expression(itW.suffix());
						} else {
							int size = words.size();
							for (int i = 0; i<size; i++) {
								Expression w0 = (Expression)words.get(i);
								Expression w1 = new Expression(w0);
								w1.addLast(new Expression(prefW));
								w0.addLast(new Expression(prefW));
								w0.removeLast();
								words.add(w1);								
							}
							w = itW.suffix();
						}
						itW = w.iterator();
					}
				}
				while (itW.hasNextTerminal()) {
					TerminalSymbol a = itW.nextTerminal();
					for (int i = 0; i<words.size(); i++) {
						Expression wi = (Expression)words.get(i);
						wi.addLast(new Expression(itW.suffix()));
					}

				}
				if (words.isEmpty()) {
					pNew.addNew(prod.getLHS(), prod.getRHS());
				} else {
					Iterator<Expression> it = words.iterator();
					while (it.hasNext()) {
						Expression w0 = (Expression)it.next();
						w0.addLast(new Expression(w));
						if (!w0.isEmpty()) {
							pNew.addNew(prod.getLHS(), w0);
						}
					}
				}
			}
		}
		p = pNew;
//		System.out.println("after epsilon rule elimination:\n"+p);
	}
	
	/** ������һ����ʽ*/
	private void eliminateUnitProductions() {
		Productions pNew = new Productions();
		
		// ������һ����ʽ
		Map<NonTerminalSymbol, Set<NonTerminalSymbol>> map = new HashMap<NonTerminalSymbol, Set<NonTerminalSymbol>>();
		Iterator<NonTerminalSymbol> itV = v.iterator();
		while (itV.hasNext()) {
			NonTerminalSymbol x = (NonTerminalSymbol)itV.next();
			Set<NonTerminalSymbol> set = new LinkedHashSet<NonTerminalSymbol>();
			set.add(x);
			map.put(x, set);
		}
		
		boolean changed;
		do {
			changed = false;
			Iterator<Production> itP = p.iterator();
			while (itP.hasNext()) {
				Production prod = (Production)itP.next();
				Expression w = prod.getRHS();
				if (w.size() == 1 && w.getFirst() instanceof NonTerminalSymbol) {
					NonTerminalSymbol x = (NonTerminalSymbol)w.getFirst();
					NonTerminalSymbol x2 = prod.getLHS();
					Set<NonTerminalSymbol> s2 = map.get(x2);
					Iterator<NonTerminalSymbol> itS2 = s2.iterator();
					while (itS2.hasNext()) {
						NonTerminalSymbol x1 = (NonTerminalSymbol)itS2.next();
						Set<NonTerminalSymbol> s2prime = map.get(x);
						if (s2prime.add(x1)) {
							changed = true;
//							System.out.println("("+x1+","+x+")");
						}
					}
				}
			}
		} while (changed);


		Iterator<Production> itP = p.iterator();
		while (itP.hasNext()) {
			Production prod = (Production)itP.next();
			Expression w = prod.getRHS();
			if (w.size() != 1 || w.getFirst() instanceof TerminalSymbol) {
				pNew.addNew(prod.getLHS(), prod.getRHS()); //ǡ���ĸ��ƻ��׼ȷ
			}
		}


		itV = v.iterator();
		while (itV.hasNext()) {
			NonTerminalSymbol x2 = (NonTerminalSymbol)itV.next();
			Set<NonTerminalSymbol> set = map.get(x2);
			Iterator<NonTerminalSymbol> itS = set.iterator();
			while (itS.hasNext()) {
				NonTerminalSymbol x1 = (NonTerminalSymbol)itS.next();
				if (x1 != x2) {
					LinkedList<Production> list = pNew.find(x2);
					if (list != null) { //���find()�ܷ��������ؿղ���ʽ
						Iterator<Production> itL = list.iterator();
						while (itL.hasNext()) {
							Production prod = (Production)itL.next();
							Expression w = prod.getRHS();
							pNew.addNew(x1, w);
						}
					}
				}
			}
		}
		p = pNew;
	}
	
	/** ��ķ˹����ʽ */
	public void toChomskyNormalForm() {

		// Step 1
		eliminateEpsilon();
//		System.out.println(this);
		
		// Step 2
		eliminateUnitProductions();
//		System.out.println(this);
		
		// Step 3
		removeUseless();
		
		// Step 4
		toChomskyNormalFormStep4();
//		System.out.println(this);
		
		// Step 5
		toChomskyNormalFormStep5();
//		System.out.println(this);
		
		System.gc();
	}


	private void toChomskyNormalFormStep4() {
		Productions pNew = new Productions();
		Map<Symbol, NonTerminalSymbol> map = new LinkedHashMap<Symbol, NonTerminalSymbol>(); // (terminal -> nonterminal(new) or null)

		int count = 0;
		Iterator<Production> it = p.iterator();
		while (it.hasNext()) {
			Production prod = (Production)it.next();
			Expression w = prod.getRHS();
			if (w.size() >= 2) { 
				pNew.addNew(prod.getLHS(), w);
				ExpressionIterator itW = w.iterator();
				while (itW.hasNext()) {
					Symbol sym = itW.next();
					if (sym instanceof TerminalSymbol) {
						NonTerminalSymbol y = (NonTerminalSymbol)map.get(sym);
						if (y == null) {
							y = v.addNew("$nt_cnf"+(count++));
							map.put(sym, y);
						}
						pNew.addNew(y, new Expression(sym));
						itW.set(y);
					}
				}
			} else {
				pNew.addNew(prod.getLHS(), w);
			}
		}
		p = pNew;
	}

	private void toChomskyNormalFormStep5() {
		Productions pNew = new Productions();
		int count = 0;
		Iterator<Production> it = p.iterator();
		while (it.hasNext()) {
			Production prod = (Production)it.next();
//			System.out.println("\nProduction:"+prod);
			Expression w = new Expression(prod.getRHS());
			if (w.size() > 2) {
				NonTerminalSymbol x = prod.getLHS();
				while (w.size() > 2) {
					Symbol sym = w.removeFirst(); 
					NonTerminalSymbol newX = v.addNew("$nt$cnf"+(count++));
					pNew.addNew(x, new Expression(sym, newX));
					x = newX;
				}
				pNew.addNew(x, new Expression(w));
			} else {				
				pNew.addNew(prod.getLHS(), w);
			}
		}
		p = pNew;
	}

	public void computeFirst() {//����First��
		if (vNullable == null) {
			computeNullable();
		}

		vFirst = new Set[v.count()];
		for (int i = 0; i<v.count(); i++) {
			vFirst[i] = new LinkedHashSet();
		}

		pFirst = new Set[p.count()];
		for (int i = 0; i<p.count(); i++) {
			pFirst[i] = new LinkedHashSet();
		}

		boolean changed;
		do {
			changed = false;
			for (int i = 0; i<v.count(); i++) {
				Iterator<Production> itL = p.iterator(v.find(i));
				while (itL.hasNext()) {
					Production prod = (Production)itL.next();
					int j = prod.getIndex();

					Expression w = prod.getRHS();
					if (!w.isEmpty()) {
						ExpressionIterator itW = w.iterator();
						boolean over = false;
						while (itW.hasNext() && !over) {
							Symbol symbol = itW.next();
							if (symbol.isTerminal()) {
								if (vFirst[i].add(symbol)) {
									changed = true;
								}
								if (pFirst[j].add(symbol)) {
									changed = true;
								}
								over = true;
							} else {
								int k = symbol.getIndex();
								if (vFirst[i].addAll(vFirst[k])) {
									changed = true;
								}
								if (pFirst[j].addAll(vFirst[k])) {
									changed = true;
								}
								if (!nullable((NonTerminalSymbol)symbol)) {
									over = true;
								}
							}
						}
					}
				}
			}
		} while (changed);
	}

	public Set<Symbol> first(NonTerminalSymbol var) {//���÷��ս��var��First��
		return vFirst[var.getIndex()];
	}

	public Set<Symbol> first(Production prod) {//���÷��ս��PROD��First��
		return pFirst[prod.getIndex()];
	}

	/** ���ñ��ʽw��First��*/
	public Set<Symbol> first(Expression w) {
		Set<Symbol> first = new LinkedHashSet<Symbol>();
		if (!w.isEmpty()) {
			ExpressionIterator itW = w.iterator();
			Symbol symbol;
			do {
				symbol = itW.next();
				if (symbol.isTerminal()) {
					first.add(symbol);
				} else {
					first.addAll(first((NonTerminalSymbol)symbol));
				}
			} while (itW.hasNext() && !symbol.isTerminal() &&
					nullable((NonTerminalSymbol)symbol));
		}
		return first;
	}

	/** ����Follow��*/
	public void computeFollow() {
		if (vFirst == null) {
			computeFirst();
		}
		vFollow = new Set[v.count()];
		for (int i = 0; i<v.count(); i++) {
			vFollow[i] = new LinkedHashSet();
		}

		vFollow[s.getIndex()].add(t.EOF);

		Iterator itV = v.iterator();
		while (itV.hasNext()) {
			NonTerminalSymbol a = (NonTerminalSymbol)itV.next();
			Iterator itL = p.find(a).iterator();
			while (itL.hasNext()) {
				Production prod = (Production)itL.next();
				Expression w = prod.getRHS();
				ExpressionIterator itW = w.iterator();
				while (itW.hasNextNonTerminal()) {
					int j = itW.nextNonTerminal().getIndex();
					vFollow[j].addAll(first(itW.suffix()));
				}
			}
		}

		boolean changed;
		do {
			changed = false;
			for (int i = 0; i<v.count(); i++) {
				List prodList = p.find(v.find(i));
				Iterator itL = prodList.iterator();
				while (itL.hasNext()) {
					Production prod = (Production)itL.next();
					Expression w = prod.getRHS();
					ExpressionIterator itW = w.iterator();
					while (itW.hasNextNonTerminal()) {
						int j = itW.nextNonTerminal().getIndex();
						if (nullable(itW.suffix())) {
							if (vFollow[j].addAll(vFollow[i])) {
								changed = true;
							}
						}
					}
				}
			}
		} while (changed);
	}

	public Set follow(NonTerminalSymbol var) {//���÷��ս��var��Follow��
		return vFollow[var.getIndex()];
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("nonterminal "+v+";\n");
		sb.append("terminal "+t+";\n");
		sb.append("start with "+s+";\n");
		sb.append(p);
		return sb.toString();
	}

	/** ��ӡ�ɿշ��ս���Ϳɿձ��ʽ*/
	public void printNullable() {
		if (vNullable != null) {
			System.out.println("nullable: ");
			Iterator itV = v.iterator();
			while (itV.hasNext()) {
				NonTerminalSymbol a = (NonTerminalSymbol)itV.next();
				if (vNullable.contains(a)) {
					System.out.println(a);
				}
			}
		} else {
			System.out.println("nullability unknown");
		}
		if (pNullable != null) {
			System.out.println("nullable productions: ");
			Iterator itP = p.iterator();
			while (itP.hasNext()) {
				Production prod = (Production)itP.next();
				if (pNullable.contains(prod)) {
					System.out.println(prod);
				}
			}
		} else {
			System.out.println("nullability of productions unknown");
		}
	}

	/** ��ӡ���ս���ͱ��ʽ��First��*/
	public void printFirst() {
		if (vFirst != null) {
			for (int i = 0; i<v.count(); i++) {
				System.out.print("first("+v.find(i).getName()+")={");
				Iterator it = t.iterator();
				while (it.hasNext()) {
					TerminalSymbol term = (TerminalSymbol)it.next();
					if (vFirst[i].contains(term)) {
						System.out.print(term+", ");
					}
				}
				System.out.println("}");
			}
		}
		if (pFirst != null) {
			for (int i = 0; i<p.count(); i++) {
				System.out.print("first("+p.find(i).getRHS()+")={");
				Iterator it = t.iterator();
				while (it.hasNext()) {
					TerminalSymbol term = (TerminalSymbol)it.next();
					if (pFirst[i].contains(term)) {
						System.out.print(term+", ");
					}
				}
				System.out.println("}");
			}
		}
	}

	/** ��ӡ���ս���ͱ��ʽ��Follow��*/
	public void printFollow() {
		if (vFollow != null) {
			for (int i = 0; i<v.count(); i++) {
				System.out.print("follow("+v.find(i).getName()+")={");
				Iterator it = t.iterator();
				while (it.hasNext()) {
					TerminalSymbol term = (TerminalSymbol)it.next();
					if (vFollow[i].contains(term)) {
						System.out.print(term+", ");
					}
				}
				System.out.println("}");
			}
		}
	}

	public boolean isInvertible() {
		return p.isInvertible();
	}
}

