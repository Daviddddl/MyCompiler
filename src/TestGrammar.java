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
     */
    public void grammar(){

    }
}
