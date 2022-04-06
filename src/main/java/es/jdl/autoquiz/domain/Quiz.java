package es.jdl.autoquiz.domain;

import lombok.Data;

import java.util.List;

@Data
public class Quiz {
    private String id;
    private String title;
    private String instructions;
    private boolean showPublic;
    private boolean showCorrect = false;
    private boolean suffleQuestions;
    private int passFraction;
    // default for questions
    private EnumNumering answernumbering;
    private boolean shuffleanswers;
    private boolean active;
    private String owner;
    // collections
    private List<String> tags;
    private List<Question> questions;
}

