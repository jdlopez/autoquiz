package es.jdl.autoquiz.domain;

import javax.validation.constraints.NotNull;

public class Answer {

	@NotNull
	private String response; // content?
	private boolean correct = false;
	private String explanation;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
}
