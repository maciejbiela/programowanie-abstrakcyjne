package io.github.maciejbiela.homework1;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistributedModuleTextFactoryTest {
    @Test
    public void createsCorrectDataAndCorrectExporterAndCorrectImporter() {
        final String textForFactory = "Ali kot zjadl dolara";
        DistributedModuleFactory factory = new DistributedModuleTextFactory(textForFactory);
        Data dataFromFactory = factory.createData();
        String textFromModule = ((TextData) dataFromFactory).getText();
        assertEquals(textForFactory, textFromModule);
        Exporter exporter = factory.createExporter();
        textFromModule = ((TextData) exporter.exportData()).getText();
        assertEquals(textForFactory, textFromModule);
        Importer importer = factory.createImporter();
        assertTrue(importer instanceof TextImporter);
    }
}