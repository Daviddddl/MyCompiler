import java.io.IOException;

public class TestGrammar {

    //private StringBuffer grammarbuffer = new StringBuffer(); // �﷨�������������
    private final String point = "->";
    private String grammarin;   //������ķ�
    private StringBuffer grammarinbuf;  //������ķ����壨���ķ��е�ע��ȥ��


    public TestGrammar() throws IOException {
        System.out.println(getInput());
    }

    /**
     * ��ȡָ��·���ļ�
     * @param fileSrc ��ȡ�ļ�·��
     */
    public TestGrammar(String fileSrc) throws IOException {

    }

    /**
     * �﷨��������
     * �����Ե����Ϸ���LR(1)���Զ�����CLOSURE(I)��GOTO()����
     * �Զ�����LR������
     * �߱��﷨��������������׼ȷ��������λ�ã����ô���ָ�����
     * ���������ʾ��Ϣ��ʽ��Error at Line[�к�]:[˵������]
     *
     *
     */
    public void grammar() throws IOException {

    }

    /**
     * ��ȡ�ʷ������Ľ��,token����
     * @return
     */
    public String getToken() throws IOException {

        String textToken = MainTest.Analyz(FileUtil.readFile("input.txt"));
        StringBuffer token = new StringBuffer();
        for(int i = 0; i < textToken.length(); i++){
            if(textToken.charAt(i) == ';' && textToken.charAt(++i) == '('){
                while(true){
                    if(textToken.charAt(i++) != ')'){
                        token.append(textToken.charAt(i));
                    }else{
                        if(textToken.charAt(i++) == ')')
                            token.append(')');
                        token.deleteCharAt(token.length()-1);
                        token.append('\n');
                        break;
                    }
                }
            }
        }
        return token.toString();
    }


    /**
     * ��ȡ�﷨�����ı����Һ���ע��
     * @return
     * @throws IOException
     */
    public String getInput() throws IOException {
        grammarin = FileUtil.readFile("grammarin.txt");
        grammarinbuf = new StringBuffer();
        for(int i = 0; i < grammarin.length(); i++){

            if(grammarin.charAt(i) == '/' && grammarin.charAt(++i) == '*'){
                while(true){
                    if(grammarin.charAt(i++) == '*' && grammarin.charAt(i) == '/')
                        break;
                }
                i++;
            }
            grammarinbuf.append(grammarin.charAt(i));
        }
        return grammarinbuf.toString();
        //return FileUtil.readFile("grammarin.txt");
    }

    /**
     * ����FIRST��
     * @return
     */
    public String myfirst(){
        return null;
    }

    /**
     * ����FOLLOW��
     * @return
     */
    public String myfollow(){
        return null;
    }

    /**
     * �Զ�����CLOSURE(1)�����ķ���
     * @return
     */
    public String myclosure(){
        return null;
    }

    /**
     * �Զ�����GOTO()����
     * @return
     */
    public String mygoto(){
        return null;
    }

    /**
     * �Զ�����LR������
     * @return
     */
    public String myLRtable(){
        return null;
    }

    /**
     * ��ӡ����﷨�����Ľ��
     * ���﷨�������������������ʽ��ӡÿ���ڵ���Ϣ
     *
     * @param result
     */
    public void printree(String result){

    }
}
