import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        /*TestLexer testLexer = new TestLexer("input.txt");
        FileUtil.clearFile();//����ļ�
        testLexer.analyse();*/
        //System.out.println(isKeyWord);

        new TestGrammar();

        //System.out.print(FileUtil.replaceBlankLine(FileUtil.readFile("input.txt")));
    }
}
