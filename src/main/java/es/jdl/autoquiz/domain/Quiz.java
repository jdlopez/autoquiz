package es.jdl.autoquiz.domain;

import java.util.List;

public class Quiz {
    private String id;
    private String title;
    private String instructions;
    private List<Question> questions;
    // some configuration (inside other object? -> configuration?)
    private boolean showCorrect = false;
    private boolean suffleQuestions;
    private int passFraction;
    private User author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setSuffleQuestions(boolean suffleQuestions) {
        this.suffleQuestions = suffleQuestions;
    }

    public boolean isSuffleQuestions() {
        return suffleQuestions;
    }

    public boolean isShowCorrect() {
        return showCorrect;
    }

    public void setShowCorrect(boolean showCorrect) {
        this.showCorrect = showCorrect;
    }

    public int getPassFraction() {
        return passFraction;
    }

    public void setPassFraction(int passFraction) {
        this.passFraction = passFraction;
    }
}
