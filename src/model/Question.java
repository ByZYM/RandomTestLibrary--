package model;

public class Question {
	//´ð°¸
	private String answer;
	
	//Ìâ¸É
	private String stem;
	
	/**
	 * @param answer	  
	 * @param stem
	 */
	public Question(String answer, String stem) {
		this.answer = answer;
		this.stem = stem;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the stem
	 */
	public String getStem() {
		return stem;
	}

	/**
	 * @param stem the stem to set
	 */
	public void setStem(String stem) {
		this.stem = stem;
	}
}
