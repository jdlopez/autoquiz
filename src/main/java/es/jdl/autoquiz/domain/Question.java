package es.jdl.autoquiz.domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String id;
    private String quizId;
    private String text;
    private EnumQuestionType type;
    private Boolean single;
    private EnumNumering answernumbering;
    private Boolean shuffleanswers;
    private List<Answer> answers;
    private List<String> tags;
}
