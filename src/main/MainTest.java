package main;

import Lexer.TestLexer;
import Parser.TestGrammar;
import Semantic.TestUnderStand;
import Util.FileUtil;

import java.io.IOException;

public class MainTest {

	/**
	 * �ʷ�����������
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String Analyz(String text) throws IOException {

		FileUtil.clearFile("./input.txt");

		FileUtil.writeFile(text, "./input.txt");

		//System.out.println(text);

		TestLexer testLexer = new TestLexer("./input.txt");

		testLexer.analyse();

		StringBuffer sb = new StringBuffer();

		String analyseString = "������";

		if(FileUtil.readFile(sb,"./output.txt")){
			analyseString = sb.toString();
		}

		return analyseString;
	}


	/**
	 * �������������
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String understand(String text) throws IOException{

		FileUtil.clearFile("./input.txt");

		FileUtil.writeFile(text, "./input.txt");

		System.out.println(text);

		TestUnderStand testUnderStand = new TestUnderStand("./input.txt");

		testUnderStand.understand();

		StringBuffer sb = new StringBuffer();

		String understandString = "������";

		if(FileUtil.readFile(sb,"./output.txt")){
			understandString = sb.toString();
		}
		return understandString;
	}

	/**
	 * �﷨����������
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String grammar(String text) throws IOException{
		FileUtil.clearFile("./grammarin.txt");

		FileUtil.writeFile(text, "./grammarin.txt");

		System.out.println(text);

		/*Parser.TestGrammar testGrammar = new Parser.TestGrammar("./grammarin.txt");

		testGrammar.grammar();*/

		new TestGrammar();

		StringBuffer sb = new StringBuffer();

		String grammarString = "������";

		if(FileUtil.readFile(sb,"./grammarOut.txt")){
			grammarString = sb.toString();
		}
		return grammarString;
	}
}
