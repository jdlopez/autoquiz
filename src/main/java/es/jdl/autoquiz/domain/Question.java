package es.jdl.autoquiz.domain;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Question {

	@Id
	private Long questionId;
	@NotNull
	private String statement;
	@Min(1) @Max(10)
	private Integer hardness;
	private List<Ref<Answer>> answers;
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
	public List<Ref<Answer>> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Ref<Answer>> answers) {
		this.answers = answers;
	}
	
}
