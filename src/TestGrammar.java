public class TestGrammar {

    private StringBuffer buffer = new StringBuffer(); // ������
    private int i = 0;
    private char ch; // �ַ�������������¶�����Դ�����ַ�
    private String strToken; // �ַ����飬��Ź��ɵ��ʷ��ŵ��ַ���

    /**
     * ��ȡָ��·���ļ�
     * @param fileSrc ��ȡ�ļ�·��
     */
    public TestGrammar(String fileSrc) {
        FileUtil.readFile(buffer, fileSrc);
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
    public void grammar(){

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
