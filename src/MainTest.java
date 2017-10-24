import java.io.IOException;

public class MainTest {

	/**
	 * 词法分析主函数
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

		String analyseString = "出错啦";

		if(FileUtil.readFile(sb,"./output.txt")){
			analyseString = sb.toString();
		}

		return analyseString;
	}


	/**
	 * 语义分析主函数
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

		String understandString = "出错啦";

		if(FileUtil.readFile(sb,"./output.txt")){
			understandString = sb.toString();
		}
		return understandString;
	}

	/**
	 * 语法分析主函数
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String grammar(String text) throws IOException{
		FileUtil.clearFile("./grammarin.txt");

		FileUtil.writeFile(text, "./grammarin.txt");

		System.out.println(text);

		/*TestGrammar testGrammar = new TestGrammar("./grammarin.txt");

		testGrammar.grammar();*/

		new TestGrammar();

		StringBuffer sb = new StringBuffer();

		String grammarString = "出错啦";

		if(FileUtil.readFile(sb,"./grammarOut.txt")){
			grammarString = sb.toString();
		}
		return grammarString;
	}
}
