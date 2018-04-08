package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Question;

public class ReadData {

	StringBuilder string = null;

	List<Question> questions = null;

	public ReadData(String fileName) throws Exception {

		//System.out.println(System.getProperty("java.class.path"));

		InputStreamReader data;
		data = new InputStreamReader(new FileInputStream("./"+fileName+".txt"));

		BufferedReader reader = new BufferedReader(data);

		string = new StringBuilder();

		String tempString = reader.readLine();

		// ��ȡ�ļ�����
		while (tempString != null) {
			string.append(tempString + "\n");
			tempString = reader.readLine();
		}
		questions = new ArrayList<Question>();
		splitQuestion();
		
	}

	// �ָ���
	private void splitQuestion() {
		if (string != null) {

			int p = 0;

			int startPoint = -1;
			int questionPoint = -1;
			int endPoint = -1;

			while (p < string.length()) {
				char tempChar = string.charAt(p);

				// �����
				if (startPoint == -1) {
					// ���������
					if (tempChar <= '9' && tempChar >= '0') {
						int t = p;

						char nextChar = string.charAt(++t);
						while (nextChar <= '9' && nextChar >= '0') {
							nextChar = string.charAt(++t);
						}

						// ��ʾΪ���
						if (nextChar == '��' || nextChar == '.' || nextChar == ' ') {
							startPoint = t + 1;
							p = startPoint;
						}
					}
				} else {

					// �Ҵ�
					if (questionPoint == -1) {
						// ���Ϊ��ĸ
						if (tempChar <= 'D' && tempChar >= 'A') {
							int point = p;
							int t = p;

							char nextChar = string.charAt(++t);
							while (nextChar == ' ') {
								nextChar = string.charAt(++t);
							}

							// ��ʾΪ���
							if (nextChar == '��' ||nextChar == ')' || nextChar == '��') {
								questionPoint = point;
								p = questionPoint;
							}
						}
					}

					// ���յ�
					// ���������
					if (tempChar <= '9' && tempChar >= '0') {
						int point = p;
						int t = p;

						char nextChar = string.charAt(++t);
						while (nextChar <= '9' && nextChar >= '0') {
							nextChar = string.charAt(++t);
						}

						// ��ʾΪ���
						if (nextChar == '��' || nextChar == '.' || nextChar == ' ') {
							endPoint = point;


							questions.add(new Question(string.charAt(questionPoint) + "",
									string.substring(startPoint, questionPoint)
											+ string.substring(questionPoint+1, endPoint)));

							startPoint = -1;
							questionPoint = -1;
							endPoint = -1;
						}
					}

				}
				p++;

			}
		}
	}

	public Question getRandomQuestion(){
		return (Question) questions.get((Math.abs(new Random().nextInt()))%questions.size());
		
	}

	public List<Question> getQuestions(){
		return questions;
	}

}
