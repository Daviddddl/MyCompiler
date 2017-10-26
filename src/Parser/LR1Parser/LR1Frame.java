package Parser.LR1Parser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Parser.ContextFreeGrammar.CFG;
import Parser.ContextFreeGrammar.TerminalsSet;

public class LR1Frame extends JFrame
{
	private JTextField NonTerminalSymbolField;
	private JTextField TerminalSymbolField;
	private JTextField startSymbolField;
	private JTextArea strToAnalyzeArea;
	private JTextArea ProductionArea;
	private JTextArea resultArea;
	private LR1Frame owner;
	
	class MyMenuBar extends JMenuBar {
		private static final long serialVersionUID = 1L;

		public MyMenuBar() {
			super();
			MenuItemListener mil = new MenuItemListener();
			
			JMenu file = new JMenu("�ļ�");
			JMenuItem clear = new JMenuItem("��ռ�¼");
			JMenuItem save = new JMenuItem("������");
			JMenuItem exit = new JMenuItem("�˳�����");
			clear.addActionListener(mil);
			save.addActionListener(mil);
			exit.addActionListener(mil);
			file.add(clear);
			file.add(save);
			file.addSeparator();
			file.add(exit);

			JMenu run = new JMenu("����");
			JMenuItem analyse = new JMenuItem("��ʼ����");
			JMenuItem defaultData = new JMenuItem("����");
			analyse.addActionListener(mil);
			defaultData.addActionListener(mil);
			run.add(analyse);
			run.addSeparator();
			run.add(defaultData);
			
			JMenu about = new JMenu("����");
			JMenuItem instruction = new JMenuItem("˵��");
			instruction.addActionListener(mil);
			JMenuItem version = new JMenuItem("�汾");
			version.addActionListener(mil);
			about.add(instruction);
			about.add(version);
			
			add(file);
			add(run);
			add(about);
		}
	}

	class MenuItemListener implements ActionListener{

		boolean simpleChoose;

		public void actionPerformed(ActionEvent e)
		{
			String Action = e.getActionCommand();
			if (Action == "�˳�����")	System.exit(0);
			
			if (Action == "��ռ�¼") {
				NonTerminalSymbolField.setText("");
				TerminalSymbolField.setText("");
				startSymbolField.setText("");
				ProductionArea.setText("");
				strToAnalyzeArea.setText("");
				resultArea.setText("");
			}
			
			if (Action == "����"){
				
				simpleChoose = !simpleChoose;
				
				if (simpleChoose) {
					NonTerminalSymbolField.setText("S,A");
					TerminalSymbolField.setText("a,b,c,d,e");
					startSymbolField.setText("S");
					ProductionArea.setText("S��aAd;\nS��bAc;\nS��aec;\nS��bed;\nA��e");
					strToAnalyzeArea.setText("aed#");
					resultArea.setText("");
				} else {
					NonTerminalSymbolField.setText("S,A,B");
					TerminalSymbolField.setText("a,b");
					startSymbolField.setText("S");
					ProductionArea.setText("S��A;\nA��BA;\nA����;\nB��aB;\nB��b");
					strToAnalyzeArea.setText("abab");
					resultArea.setText("");
				}
			}
			if (Action == "��ʼ����") {
				//�����������޹�
				CFG grammar = new CFG(NonTerminalSymbolField.getText(), 
						TerminalSymbolField.getText(), 
						startSymbolField.getText(),
						ProductionArea.getText());
				TerminalsSet toAnalyze = new TerminalsSet(strToAnalyzeArea.getText());
				//����
				LR1Parser lr1Parser = new LR1Parser(grammar, toAnalyze);
				lr1Parser.precompute();//Ԥ���㣬����LR(1)������
				try {
					resultArea.setText(lr1Parser.getProcess(grammar)+lr1Parser.parse()+"\n\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (SyntaxError e1) {
					e1.printStackTrace();
				}
			}
			if (Action == "������")
			{
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = 
					new FileNameExtensionFilter("TXT Documents", new String[] {"txt"});
				fileChooser.setSelectedFile(
						new File( new StringBuilder("*").append(".txt").toString() )
						);
				fileChooser.setFileFilter(filter);
				int i = fileChooser.showSaveDialog(owner);
				if (i == 0) {
					File file = fileChooser.getSelectedFile();
					try {
						file.createNewFile();
						FileWriter fw = new FileWriter(file);
						fw.write(resultArea.getText());
						fw.close();
					} catch (IOException ioexception) {
						JOptionPane.showMessageDialog(null, ioexception.toString(), "�޷�����!", 0);
					}
				}
			}
			if (Action == "˵��")
			{
				JDialog jd = new JDialog(owner, "����ʹ��˵��", true);
				String str = "  tip 1. �ս�������ս����Ӣ�Ķ��Ÿ���\n";
				str = (new StringBuilder(String.valueOf(str))).append("  tip 2. ����ʽ�Էֺ�Ӣ�ĸ���\n" +
						"         ���Ƶ�����Ϊ��'��'��\n").toString();
				str = (new StringBuilder(String.valueOf(str))).append("  tip 3. �����д�������������ݣ�\n" +
						"         �����\"����->ʹ��Ĭ������\"\n").toString();
				TextArea ta = new TextArea(str);
				ta.setEditable(false);
				jd.add(ta);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				jd.setBounds(screen.width / 2 - 200, screen.height / 2 - 150, 400, 300);
				jd.setVisible(true);
			}
			if (Action == "�汾")
			{
				JDialog jd = new JDialog(owner, "�汾˵��", true);
				String str = "  LR(1)�﷨������\n";
				str = (new StringBuilder(String.valueOf(str))).append("  By: Jinlin\n").toString();
				TextArea ta = new TextArea(str);
				ta.setEditable(false);
				jd.add(ta);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				jd.setBounds(screen.width / 2 - 200, screen.height / 2 - 150, 400, 300);
				jd.setVisible(true);
			}
		}

		MenuItemListener()
		{
			super();
			simpleChoose = false;
		}
	}


	public LR1Frame()
	{
		super("LR(1)�﷨����");
		owner = this;
		init();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((fullScreen.width - 800) / 2, (fullScreen.height - 600) / 2, 800, 600);
		setResizable(false);
		setVisible(true);
	}

	public void init()
	{
		getContentPane().setLayout(null);
		
		//�˵���
		setJMenuBar(new MyMenuBar());
		
		/** �������*/
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 0, 300, 545);
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setBorder(new TitledBorder(new EtchedBorder(), "�ڴ���������"));
		getContentPane().add(inputPanel);
		inputPanel.setLayout(null);
		//Symbol���봦
		JLabel label1 = new JLabel("���ս����");
		JLabel label2 = new JLabel("�ս����");
		JLabel label3 = new JLabel("��ʼ����");
		label1.setBounds(15, 20, 100, 25);
		label2.setBounds(15, 50, 100, 25);
		label3.setBounds(15, 80, 100, 25);
		inputPanel.add(label1);
		inputPanel.add(label2);
		inputPanel.add(label3);
		
		NonTerminalSymbolField = new JTextField();
		TerminalSymbolField = new JTextField();
		startSymbolField = new JTextField();
		NonTerminalSymbolField.setBounds(80, 20, 210, 25);
		TerminalSymbolField.setBounds(80, 50, 210, 25);
		startSymbolField.setBounds(80, 80, 210, 25);
		inputPanel.add(NonTerminalSymbolField);
		inputPanel.add(TerminalSymbolField);
		inputPanel.add(startSymbolField);
		//Production���봦
		JPanel productionPanel = new JPanel();
		productionPanel.setBounds(5, 110, 290, 300);
		productionPanel.setLayout(new BorderLayout());
		productionPanel.setBorder(new TitledBorder(new EtchedBorder(), "����ʽ��"));
		ProductionArea = new JTextArea();
		JScrollPane jsp1 = new JScrollPane(ProductionArea);
		productionPanel.add(jsp1);
		inputPanel.add(productionPanel);
		//������Code
		JPanel strToAnalyzePanel = new JPanel();
		strToAnalyzePanel.setBounds(5, 410, 290, 130);
		strToAnalyzePanel.setLayout(new BorderLayout());
		strToAnalyzePanel.setBorder(new TitledBorder(new EtchedBorder(), "����Code��"));
		strToAnalyzeArea = new JTextArea();
		JScrollPane jsp0 = new JScrollPane(strToAnalyzeArea);
		strToAnalyzePanel.add(jsp0);
		inputPanel.add(strToAnalyzePanel);
		
		
		//������
		JPanel resultPanel = new JPanel();
		resultPanel.setBounds(305, 0, 490, 545);
		resultPanel.setLayout(new BorderLayout());
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "������"));
		getContentPane().add(resultPanel);
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		JScrollPane jsp2 = new JScrollPane(resultArea);
		resultPanel.add(jsp2);
	}

	public static void main(String args[])
	{
		new LR1Frame();
	}
}
