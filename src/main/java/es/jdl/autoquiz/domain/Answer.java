package es.jdl.autoquiz.domain;

import lombok.Data;

@Data
public class Answer {
    private String id;
    private String quizId;
    private String questionId;
    private Integer fraction;
    private String text;
    private String feedback;
}
