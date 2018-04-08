package view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.ReadData;
import model.Question;

public class MainFrame extends JFrame implements ActionListener {

	ArrayList<JButton> buttonsMain = new ArrayList<>();

	ArrayList<JButton> buttonsQuestion = new ArrayList<>();

	JPanel panelCard = new JPanel();
	JPanel panelMain = new JPanel();
	JPanel panelQuestion = new JPanel();

	ReadData RD;

	JTextArea jt = new JTextArea();

	CardLayout card = new CardLayout();
	
	boolean hasAnswered=false;
	Question questionNow;
	
	private void Init() {
		this.setTitle("��У֪ʶ��ѡ����ѵ��ϵͳ");
		setSize(300, 300);
		setLocationRelativeTo(null);

		panelCard.setLayout(card);

		///////////////
		panelMain.setLayout(new GridLayout(5, 1));

		buttonsMain.add(new JButton("���Ļ���֪ʶ"));
		buttonsMain.add(new JButton("��Сƽ����"));
		buttonsMain.add(new JButton("ë��˼��"));
		buttonsMain.add(new JButton("������ϰ"));
		buttonsMain.add(new JButton("�˳�ϵͳ"));

		for (JButton j : buttonsMain) {
			panelMain.add(j);
			j.addActionListener(this);
		}
		///////////////

		///////////////
		panelQuestion.setLayout(new GridLayout(2, 1));
		//jt.setLineWrap(true);
		jt.setEditable(false);
		
		panelQuestion.add(new JScrollPane(jt));

		JPanel panelBig = new JPanel(new GridLayout(2, 1));
		JPanel panelTop = new JPanel(new GridLayout(1, 4));// ABCD
		JPanel panelBottom = new JPanel(new GridLayout(1, 2));// ��һ�� ����

		buttonsQuestion.add(new JButton("A"));
		buttonsQuestion.add(new JButton("B"));
		buttonsQuestion.add(new JButton("C"));
		buttonsQuestion.add(new JButton("D"));
		buttonsQuestion.add(new JButton("��һ��"));
		buttonsQuestion.add(new JButton("����"));

		for (int i = 0; i < buttonsQuestion.size(); i++) {
			if (i >= 0 && i <= 3) {
				panelTop.add(buttonsQuestion.get(i));
			} else {
				panelBottom.add(buttonsQuestion.get(i));
			}

			buttonsQuestion.get(i).addActionListener(this);
		}

		panelQuestion.add(panelBig);
		panelBig.add(panelTop);
		panelBig.add(panelBottom);

		///////////////

		panelCard.add(panelMain, "��ҳ");
		panelCard.add(panelQuestion, "����");

		this.add(panelCard);
		this.setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public MainFrame() {
		Init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JButton) {
			JButton j = (JButton) obj;

			for (JButton jb : buttonsMain) {
				if (jb == j) {
					if (j.getText().equals("�˳�ϵͳ")) {
						System.exit(0);
					} else if (j.getText().equals("������ϰ")) {

					} else {
						card.show(panelCard, "����");
						this.setTitle(j.getText());
						try {
							RD = new ReadData(j.getText());
							questionNow=RD.getRandomQuestion();
							jt.setText(questionNow.getStem());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					break;
				}
			}

			for (JButton jb : buttonsQuestion) {
				if (jb == j) {
					if (j.getText().equals("����")) {
						jt.setText("");
						hasAnswered=false;
						this.setTitle("��У֪ʶ��ѡ����ѵ��ϵͳ");
						card.show(panelCard, "��ҳ");
					} else if (j.getText().equals("��һ��")) {
						
						if(jt.getText().equals("")){
							questionNow=RD.getRandomQuestion();
							jt.setText(questionNow.getStem());
						}
						else if(hasAnswered==false){
							//�������Ҷ���
							shock();
						}
						else{
							hasAnswered=false;
							questionNow=RD.getRandomQuestion();
							jt.setText(questionNow.getStem());
						}
					} else {
						
						if(hasAnswered==false && !jt.getText().equals("")){
							hasAnswered=true;
							
							if(questionNow.getAnswer().equals(j.getText())){
								jt.setText(jt.getText()+"Y ������ȷ����ȷ��Ϊ"+questionNow.getAnswer());
							}else{
								jt.setText(jt.getText()+"N ���������ȷ��Ϊ"+questionNow.getAnswer());
								//�������Ҷ���
								shock();
							}
							
						}else{
							
						}
						/*
						 * try { RD=new ReadData(j.getText()); } catch
						 * (Exception e1) { e1.printStackTrace(); }
						 */
					}
					break;
				}
			}

		}
	}

	//���Ҷ���
	private void shock(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int x=100;
				while(x>0){
					JFrame j=MAIN.MAIN.mf;
					
					if(x%20>=10){
						j.setBounds(j.getX()-1, j.getY(), j.getWidth(), j.getHeight());
					}else{
						j.setBounds(j.getX()+1, j.getY(), j.getWidth(), j.getHeight());
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
					x--;
				}
				
				
			}
		}).start();
	}
	
}
