package io.github.maciejbiela.homework1;

public class TextData
        implements Data {
    private String text;

    public TextData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
