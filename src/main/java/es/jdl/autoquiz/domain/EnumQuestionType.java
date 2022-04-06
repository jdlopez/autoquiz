package es.jdl.autoquiz.domain;

public enum EnumQuestionType {
    //<question type="multichoice|truefalse|shortanswer|matching|cloze|essay|numerical|description">
    multichoice("multichoice"),
    truefalse("truefalse"),
    shortanswer("shortanswer"),
    matching("matching"),
    essay("essay"),
    numerical("numerical");

    private String label;

    EnumQuestionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
