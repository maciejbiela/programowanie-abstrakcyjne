package io.github.maciejbiela.homework1;

public interface DistributedModuleFactory {
    Data createData();

    Exporter createExporter();

    Importer createImporter();
}
