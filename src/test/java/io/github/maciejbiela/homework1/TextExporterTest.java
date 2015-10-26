package io.github.maciejbiela.homework1;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextExporterTest {
    @Test
    public void exportsCorrectlyAndClearsData() {
        String textToBeExported = "Ala ma kota";
        Exporter exporter = new TextExporter(textToBeExported);
        Data exportData = exporter.exportData();
        String exportedText = ((TextData) exportData).getText();
        assertEquals(textToBeExported, exportedText);
        exportData = exporter.exportData();
        exportedText = ((TextData) exportData).getText();
        textToBeExported = "";
        assertEquals(textToBeExported, exportedText);
    }
}