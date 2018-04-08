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
		this.setTitle("党校知识点选择题训练系统");
		setSize(300, 300);
		setLocationRelativeTo(null);

		panelCard.setLayout(card);

		///////////////
		panelMain.setLayout(new GridLayout(5, 1));

		buttonsMain.add(new JButton("党的基础知识"));
		buttonsMain.add(new JButton("邓小平理论"));
		buttonsMain.add(new JButton("毛泽东思想"));
		buttonsMain.add(new JButton("错题练习"));
		buttonsMain.add(new JButton("退出系统"));

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
		JPanel panelBottom = new JPanel(new GridLayout(1, 2));// 下一题 返回

		buttonsQuestion.add(new JButton("A"));
		buttonsQuestion.add(new JButton("B"));
		buttonsQuestion.add(new JButton("C"));
		buttonsQuestion.add(new JButton("D"));
		buttonsQuestion.add(new JButton("下一题"));
		buttonsQuestion.add(new JButton("返回"));

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

		panelCard.add(panelMain, "主页");
		panelCard.add(panelQuestion, "问题");

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
					if (j.getText().equals("退出系统")) {
						System.exit(0);
					} else if (j.getText().equals("错题练习")) {

					} else {
						card.show(panelCard, "问题");
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
					if (j.getText().equals("返回")) {
						jt.setText("");
						hasAnswered=false;
						this.setTitle("党校知识点选择题训练系统");
						card.show(panelCard, "主页");
					} else if (j.getText().equals("下一题")) {
						
						if(jt.getText().equals("")){
							questionNow=RD.getRandomQuestion();
							jt.setText(questionNow.getStem());
						}
						else if(hasAnswered==false){
							//窗口左右抖动
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
								jt.setText(jt.getText()+"Y 答题正确，正确答案为"+questionNow.getAnswer());
							}else{
								jt.setText(jt.getText()+"N 答题错误，正确答案为"+questionNow.getAnswer());
								//窗口左右抖动
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

	//左右抖动
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
