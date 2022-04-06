package es.jdl.autoquiz.domain;

public enum EnumNumering {
    NONE("NONE"), LETTERS("LETTERS"), NUMBERS("NUMBERS");

    private String label;

    EnumNumering(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
