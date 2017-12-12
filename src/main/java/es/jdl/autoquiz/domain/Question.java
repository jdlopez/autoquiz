package es.jdl.autoquiz.domain;

import java.util.List;

public class Question {
    private String id;
    private String text;
    private EnumQuestionType type;
    private List<Answer> answers;
    private boolean shuffleanswers;
    private boolean single;
    private EnumNumering answernumbering;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EnumQuestionType getType() {
        return type;
    }

    public void setType(EnumQuestionType type) {
        this.type = type;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isShuffleanswers() {
        return shuffleanswers;
    }

    public void setShuffleanswers(boolean shuffleanswers) {
        this.shuffleanswers = shuffleanswers;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public EnumNumering getAnswernumbering() {
        return answernumbering;
    }

    public void setAnswernumbering(EnumNumering answernumbering) {
        this.answernumbering = answernumbering;
    }
}
