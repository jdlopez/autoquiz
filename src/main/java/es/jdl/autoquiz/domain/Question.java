package es.jdl.autoquiz.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

	@Id
	private Long questionId;
	@Index
	private Key<Topic> topic;
	@NotNull
	private String statement;
	@Index
	@Min(1) @Max(10)
	private Integer hardness = 5;
	private List<Answer> answers = new ArrayList<>();

	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public Integer getHardness() {
		return hardness;
	}
	public void setHardness(Integer hardness) {
		this.hardness = hardness;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
}
