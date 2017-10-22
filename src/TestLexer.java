/**
 * �ʷ�����
 * �ؼ��֣������һ��һ��  
 * ��ʶ�����������ָ�������һ��
 * �����δ����������� ++��--��+= ��
 */
public class TestLexer extends TypeUtil{
	private StringBuffer buffer = new StringBuffer(); // ������
	private int i = 0;
	private char ch; // �ַ�������������¶�����Դ�����ַ�
	private String strToken; // �ַ����飬��Ź��ɵ��ʷ��ŵ��ַ���
	private String DFAStr = " "; //DFAת����
	private String tab = ") &nbsp;&nbsp;&nbsp;&nbsp;";
	
	public TestLexer() {
	}
	/**
	 * ��ȡָ��·���ļ�
	 * @param fileSrc ��ȡ�ļ�·��
	 */
	public TestLexer(String fileSrc) {
		FileUtil.readFile(buffer, fileSrc);
	}

	/**
	 * �ʷ�����
	 */
	public void analyse() {
		strToken = ""; // ��strTokenΪ�մ�
		FileUtil.clearFile();//����ļ�
		int error = 0; //����ĸ���
		while (i < buffer.length()) {
			getChar();
			getBC();
			if (isLetter(ch)) { // ���chΪ��ĸ
				while (isLetter(ch) || isDigit(ch)) {
					concat();
					getChar();
				}
				retract(); // �ص�
				if (isKeyWord(strToken)) { 
					writeFile(strToken,"_"+tab+"    <0,letter_,1>");//strTokenΪ�ؼ���
				} else { 
					writeFile("IDN",strToken +tab+ "    <0,letter_,1> <1,letter/digit,1>");//strTokenΪ��ʶ��
				}
				strToken = "";
			} else if (isDigit(ch)) {
				if(ch == '0'){
					getChar();
					if(ch != '.' && isSeparators(ch)){
						writeFile("DEC", "0"+tab+ "    <0,digit,2> <2,0,4/5/7>");
					}else if(ch =='.'){
						getChar();
						while (isDigit(ch)) {//chΪ����
							concat();
							getChar();
						}
						writeFile("FLOAT","0." + strToken+tab+ "    <0,digit,2> <2,1-9,3> <3,0-9,3>"); // �Ǹ�����
						strToken = "";
					}
					else if(ch == 'x'){
						boolean err = false;
						while (isDigit(ch) || isLetter(ch)) {//chΪ���ֻ�����ĸ
							concat();
							getChar();
							if(!isHEXletter(ch))
								err = true;
						}
						if(err){
							retract(); // �ص�
							writeFile("HEX","0"+strToken +tab+ "    <0,digit,2> " +
									"<2,0,4/5/7> <4/5/7,x,8> <8,0-9/a-f/A-F,9> <9,0-9/a-f/A-F,9>"); // ��ʮ����������

						}else{
							error++;
							writeFile("ERROR","0" + strToken+tab); // �Ƿ�
						}
						strToken = "";
					}else{
						boolean err = false;
						while (isDigit(ch)) {//chΪ����
							if(ch > '7')
								err = true;
							concat();
							getChar();

						}
						if(!err && !isLetter(ch)){//��������+��ĸ
							retract(); // �ص�
							writeFile("OCT","0"+strToken+tab+ "    <0,digit,2> " +
									"<2,0,4/5/7> <4/5/7,0-7,6> <6,0-7,6>"); // �ǰ˽�������
						}else{
							error++;
							writeFile("ERROR","0" + strToken+tab); // �Ƿ�
						}
						strToken = "";
					}
				}else{
					boolean sciencedigit = false;
					boolean floatdigit = false;
					while (isDigit(ch)||ch == 'e' ||ch == 'E' || ch =='.') {//chΪ���ֻ�e��.
						concat();
						getChar();
						if(ch == 'E' || ch == 'e')
							sciencedigit = true;
						if(ch == '.')
							floatdigit = true;
					}
					if(!isLetter(ch) ){//��������+��ĸ
						if(!sciencedigit && !floatdigit){
							retract(); // �ص�
							writeFile("DEC",strToken+tab+ "    <0,digit,2> <2,1-9,3> <3,0-9,3>"); // ��ʮ��������
						}else if(!sciencedigit && floatdigit){
							retract(); // �ص�
							writeFile("FLOAT",strToken+tab+ "    <0,digit,2> <2,1-9,3> <3,0-9,3>"); // �Ǹ�����
						}else if(sciencedigit && !floatdigit){
							retract(); // �ص�
							writeFile("SN",strToken+tab+ "    <0,digit,2> <2,1-9,3> <3,0-9,3>"); // �ǿ�ѧ������
						}else{
							retract(); // �ص�
							writeFile("FLOAT SN",strToken+tab+ "    <0,digit,2> <2,1-9,3> <3,0-9,3>"); // ��ʮ��������
						}

					}else {
						error++;
						writeFile("ERROR",strToken+tab); // �Ƿ�
					}
					strToken = "";
				}
			} else if (isOperator(ch)) { //�����
				if(ch == '/'){
					getChar();
					if(ch == '*') {//Ϊ/*ע��
						while(true){
							getChar();
							if(ch == '*'){// Ϊ����ע�ͽ���
								getChar();
								if(ch == '/') {
									writeFile("NOTE","/**/"+tab+ "    <0,/,14> <14,*,15> " +
											"<15,others,15> <15,*,16> <16,others,15> <16,*,16> <16,/,17>");
									break;
								}
							}
						}
					}/*else if(ch == '/'){

						while(ch != '\n'){
							//System.out.println(ch+"   "+(int)ch);
							getChar();
						}
						writeFile("NOTE","//");
					}*/
					//retract();
				}else {
					//System.out.println(ch+"   "+(int)ch);
					switch (ch) {
						case '+':
							//writeFile("plus", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '-':
							//writeFile("min", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '*':
							//writeFile("mul", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '/':
							//writeFile("div", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '>':
							//writeFile("gt", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '<':
							//writeFile("lt", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '=':
							//writeFile("eq", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '&':
							//writeFile("and", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '|':
							//writeFile("or", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						case '~':
							//writeFile("not", ch + "");
							writeFile(ch +"", "_"+tab+"    <0,operator,19> <19,operator,19>");
							break;
						default:
							break;
					}
				}
			} else if (isSeparators(ch)) { // ���
				//writeFile("separators",ch+"");
				writeFile(ch +"", "_"+tab+"    <0,separator,18>");
			} else {
				error++;
				writeFile("ERROR",ch+"" + tab);
			}
		}
		writeFile("<br>There were " + error +" errors.<br><br>Output DFA:<br>");
		writeFile(HTMLDFA.DFAStr);
	}

	/**
	 * ����һ�������ַ�����ch�У�����ָʾ��ǰ��һ���ַ�
	 */
	public void getChar() {
		ch = buffer.charAt(i);
		i++;
	}
	/** ���ch�е��ַ��Ƿ�Ϊ�հף����������getChar()ֱ��ch�н���һ���ǿհ��ַ�*/
	public void getBC() {
		//isSpaceChar(char ch) ȷ��ָ���ַ��Ƿ�Ϊ Unicode �հ��ַ���
		//������������ʶ���з�
		while (Character.isWhitespace(ch))//ȷ��ָ���ַ����� Java ��׼�Ƿ�Ϊ�հ��ַ���
			getChar();
	}

	/**��ch���ӵ�strToken֮��*/
	public void concat() {
		strToken += ch;
	}
	/** ������ָʾ���ص�һ���ַ�λ�ã���chֵΪ�հ��� */
	public void retract() {
		i--;
		ch = ' ';
	}
	/**
	 * ���ն�Ԫʽ����д���ļ�
	 * @param file �ַ�����
	 * @param s	��ǰ�ַ�
	 */
	public void writeFile(String file,String s) {
		int temp = getType(file.toUpperCase());
		System.out.println("("+file+", "+s + DFAStr);
		file = "("+file+", "+s+"\r\n<br>";
		FileUtil.writeFile(file);
	}

	public void writeFile(String s){
		System.out.println(s);
		FileUtil.writeFile(s);
	}
}