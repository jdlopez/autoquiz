package es.jdl.autoquiz.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Topic {
	@Id
	private String topicId;
	@Index
	@NotNull
	private String name;
	@Index
	@NotNull
	@JsonProperty("abstract")
	private String abstractText;
	private String content;
	@Index
	private List<String> categories;
	@Index
	private List<Long> courses;
	
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbstractText() {
		return abstractText;
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<Long> getCourses() {
		return courses;
	}
	public void setCourses(List<Long> courses) {
		this.courses = courses;
	}
}
