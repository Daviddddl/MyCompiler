import java.io.IOException;

public class MainTest {

	/**
	 * �ʷ�����������
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static String Analyz(String text) throws IOException {
		/*�����ʷ�������*/
		/*TestLexer testLexer = new TestLexer("./src/input.txt");
		//FileUtil.clearFile();//����ļ�
		testLexer.analyse();*/

		FileUtil.clearFile("./input.txt");

		FileUtil.writeFile(text, "./input.txt");

		System.out.println(text);

		TestLexer testLexer = new TestLexer("./input.txt");

		testLexer.analyse();

		StringBuffer sb = new StringBuffer();

		String analyseString = "������";

		if(FileUtil.readFile(sb,"./output.txt")){
			analyseString = sb.toString();
		}

		return analyseString;
		//System.out.println(FileUtil.readFile("./src/output.txt"));
	}
}
