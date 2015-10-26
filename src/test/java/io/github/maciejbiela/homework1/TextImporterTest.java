package io.github.maciejbiela.homework1;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextImporterTest {
    @Test
    public void importsDataCorrectly() {
        String textToBeImported = "Ala zgubila dolara";
        Data dataToSendToImporter = new TextData(textToBeImported);
        Importer importer = new TextImporter();
        importer.importData(dataToSendToImporter);
        String dataSavedInImporter = ((TextImporter) importer).importedText();
        assertEquals(textToBeImported, dataSavedInImporter);
    }
}