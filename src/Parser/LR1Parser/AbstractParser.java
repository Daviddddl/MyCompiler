package Parser.LR1Parser;

import Parser.ContextFreeGrammar.*;
import java.io.*;

public abstract class AbstractParser implements Parser {

	protected CFG g;//�������޹��ķ�
	protected NonTerminalsSet v;//���ս����
	protected TerminalsSet t;//�ս����
	protected TerminalsSet toAnalyzeTermianls;//�������ս����
	protected NonTerminalSymbol s;//��ʼ��
	protected Productions p;//����ʽ��
	protected Production sp;//��ʼ����ʽ
	

	public AbstractParser(CFG gammar, TerminalsSet toAnalyze, boolean addStartProduction) {
		g = gammar;
		if (addStartProduction) {
			sp = g.addStartProduction();
		}
		v = g.getNonTerminals();
		t = g.getTerminals();
		s = g.getStartSymbol();
		p = g.getProductions();
		toAnalyzeTermianls = toAnalyze;
	}
	
	public CFG getGrammar() {
		return g;
	}

	abstract public void precompute();
	abstract public String parse() throws IOException, SyntaxError;
}
