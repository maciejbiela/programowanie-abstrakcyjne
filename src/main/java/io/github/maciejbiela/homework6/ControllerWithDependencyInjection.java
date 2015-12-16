package io.github.maciejbiela.homework6;

public class ControllerWithDependencyInjection implements ControllerTemplateMethod {
    private ObjectsConstructor objectsConstructor;
    private ConstructionRecipeCreator constructionRecipeCreator;
    private Logger logger;
    private ProductionLineMover productionLineMover;

    public ControllerWithDependencyInjection(ObjectsConstructor objectsConstructor,
                                             ConstructionRecipeCreator constructionRecipeCreator,
                                             Logger logger,
                                             ProductionLineMover productionLineMover) {
        this.objectsConstructor = objectsConstructor;
        this.constructionRecipeCreator = constructionRecipeCreator;
        this.logger = logger;
        this.productionLineMover = productionLineMover;
    }

    @Override
    public void execute() {
        final ConstructionRecipe constructionRecipe = constructionRecipeCreator.getConstructionRecipe();
        final int numberOfElementsToProduce = constructionRecipeCreator.getNumberOfElementsToProduce();

        if (couldMoveProductionLineToStartingPosition()) {
            constructObjectsFromRecipe(constructionRecipe, numberOfElementsToProduce);
        }
    }

    private void constructObjectsFromRecipe(ConstructionRecipe constructionRecipe, int numberOfElementsToProduce) {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            tryToConstructObjectFromRecipe(constructionRecipe);
        }
    }

    private void tryToConstructObjectFromRecipe(ConstructionRecipe constructionRecipe) {
        boolean canConstructTheObject = objectsConstructor.constructObjectFromRecipe(constructionRecipe);
        if (canConstructTheObject) {
            constructTheObject(constructionRecipe);
        } else {
            informAboutInabilityToCreateTheObject();
        }
    }

    private void informAboutInabilityToCreateTheObject() {
        logger.log(LoggingType.WARNING, "Could not create object from recipe");
        try {
            productionLineMover.moveProductionLine(MovingDirection.TO_SCRAN);
        } catch (Exception e) {
            logger.log(LoggingType.ERROR, "Can't move not constructed car to scan, scan is full!");
        }
    }

    private void constructTheObject(ConstructionRecipe constructionRecipe) {
        try {
            productionLineMover.moveProductionLine(MovingDirection.FORWARD);
            logger.log(LoggingType.INFO, constructionRecipe.getNameOfObject() + " created!");
        } catch (Exception e) {
            logger.log(LoggingType.ERROR, "Can't move ProductionLine!");
        }
    }

    private boolean couldMoveProductionLineToStartingPosition() {
        try {
            return productionLineMover.moveProductionLine(MovingDirection.FORWARD);
        } catch (Exception e) {
            logger.log(LoggingType.ERROR, "Can't move ProductionLine!");
        }
        return false;
    }
}
