package io.github.maciejbiela.homework1;

public class TextExporter
        implements Exporter {
    private String text;

    public TextExporter(String text) {
        this.text = text;
    }

    @Override
    public Data exportData() {
        TextData textData = new TextData(text);
        text = "";
        return textData;
    }
}
