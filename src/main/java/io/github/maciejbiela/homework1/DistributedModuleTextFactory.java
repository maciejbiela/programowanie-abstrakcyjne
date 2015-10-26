package io.github.maciejbiela.homework1;

public class DistributedModuleTextFactory
        implements DistributedModuleFactory {
    private String text;

    public DistributedModuleTextFactory(String text) {
        this.text = text;
    }

    @Override
    public Data createData() {
        return new TextData(text);
    }

    @Override
    public Exporter createExporter() {
        return new TextExporter(text);
    }

    @Override
    public Importer createImporter() {
        return new TextImporter();
    }
}
