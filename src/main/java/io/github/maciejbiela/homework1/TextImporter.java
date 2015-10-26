package io.github.maciejbiela.homework1;

public class TextImporter
        implements Importer {
    private String text;

    @Override
    public void importData(Data data) {
        try {
            text = ((TextData) data).getText();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public String importedText() {
        return text;
    }
}
