public class test {
    public static void main(String[] args){
        TestLexer testLexer = new TestLexer("input.txt");
        FileUtil.clearFile();//����ļ�
        testLexer.analyse();
        //System.out.println(isKeyWord);
    }
}
