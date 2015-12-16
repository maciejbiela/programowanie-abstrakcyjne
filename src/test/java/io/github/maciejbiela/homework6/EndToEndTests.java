package io.github.maciejbiela.homework6;

import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EndToEndTests {
    ObjectsConstructor objectsConstructor;
    ConstructionRecipeCreator constructionRecipeCreator;
    Logger logger;
    ProductionLineMover productionLineMover;
    ConstructionRecipe constructionRecipe;
    ControllerTemplateMethod controller;

    final String nameOfObject = "UJ's first car";
    int numberOfObjectsToConstruct = 1;

    public EndToEndTests() {
        objectsConstructor = mock(ObjectsConstructor.class);
        constructionRecipeCreator = mock(ConstructionRecipeCreator.class);
        logger = mock(Logger.class);
        productionLineMover = mock(ProductionLineMover.class);
        constructionRecipe = mock(ConstructionRecipe.class);
        controller = new ControllerWithDependencyInjection(
                objectsConstructor, constructionRecipeCreator, logger, productionLineMover);
    }

    @Test
    public void testConstructionOfSingleObject() {
        final boolean wasSuccessful = true;
        when(constructionRecipe.getNameOfObject()).thenReturn(nameOfObject);
        when(constructionRecipeCreator.getConstructionRecipe()).thenReturn(constructionRecipe);
        when(constructionRecipeCreator.getNumberOfElementsToProduce()).thenReturn(numberOfObjectsToConstruct);
        when(productionLineMover.moveProductionLine(MovingDirection.FORWARD)).thenReturn(wasSuccessful);
        when(objectsConstructor.constructObjectFromRecipe(constructionRecipe)).thenReturn(wasSuccessful);
        when(logger.log(eq(LoggingType.INFO), anyString())).thenReturn(null);

        controller.execute();

        verify(constructionRecipeCreator, times(1)).getConstructionRecipe();
        verify(constructionRecipeCreator, times(1)).getNumberOfElementsToProduce();
        verify(objectsConstructor, times(1)).constructObjectFromRecipe(constructionRecipe);
        verify(logger, times(1)).log(eq(LoggingType.INFO), anyString());
        verify(productionLineMover, times(2)).moveProductionLine(MovingDirection.FORWARD);
    }

    @Test()
    public void testConstructionFailureMovingProductionLineFailed() {
        when(constructionRecipe.getNameOfObject()).thenReturn(nameOfObject);
        when(constructionRecipeCreator.getConstructionRecipe()).thenReturn(constructionRecipe);
        when(constructionRecipeCreator.getNumberOfElementsToProduce()).thenReturn(numberOfObjectsToConstruct);
        when(productionLineMover.moveProductionLine(MovingDirection.FORWARD)).thenThrow(Exception.class);
        when(logger.log(eq(LoggingType.ERROR), anyString())).thenReturn(null);

        controller.execute();

        verify(constructionRecipeCreator, times(1)).getConstructionRecipe();
        verify(constructionRecipeCreator, times(1)).getConstructionRecipe();
        verify(objectsConstructor, never()).constructObjectFromRecipe(constructionRecipe);
        verify(logger, never()).log(eq(LoggingType.INFO), anyString());
        verify(productionLineMover).moveProductionLine(MovingDirection.FORWARD);
    }

    @Test
    public void testConstructionFailureObjectConstructionFailed() {
        boolean wasSuccessful = true;
        when(constructionRecipe.getNameOfObject()).thenReturn(nameOfObject);
        when(constructionRecipeCreator.getConstructionRecipe()).thenReturn(constructionRecipe);
        when(constructionRecipeCreator.getNumberOfElementsToProduce()).thenReturn(numberOfObjectsToConstruct);
        when(productionLineMover.moveProductionLine(MovingDirection.FORWARD)).thenReturn(wasSuccessful);
        when(objectsConstructor.constructObjectFromRecipe(constructionRecipe)).thenReturn(wasSuccessful = false);
        when(logger.log(eq(LoggingType.WARNING), anyString())).thenReturn(null);
        when(productionLineMover.moveProductionLine(MovingDirection.TO_SCRAN)).thenReturn(wasSuccessful);

        controller.execute();

        verify(constructionRecipeCreator, times(1)).getConstructionRecipe();
        verify(constructionRecipeCreator, times(1)).getNumberOfElementsToProduce();
        verify(objectsConstructor, times(1)).constructObjectFromRecipe(constructionRecipe);
        verify(logger, times(1)).log(eq(LoggingType.WARNING), anyString());
        verify(productionLineMover, times(1)).moveProductionLine(MovingDirection.FORWARD);
        verify(productionLineMover, times(1)).moveProductionLine(MovingDirection.TO_SCRAN);
    }

    @Test()
    public void testConstructionFailureObjectConstructionFailedThenMovingToScanAlsoFailed() {
        boolean wasSuccessful = true;
        when(constructionRecipe.getNameOfObject()).thenReturn(nameOfObject);
        when(constructionRecipeCreator.getConstructionRecipe()).thenReturn(constructionRecipe);
        when(constructionRecipeCreator.getNumberOfElementsToProduce()).thenReturn(numberOfObjectsToConstruct);
        when(productionLineMover.moveProductionLine(MovingDirection.FORWARD)).thenReturn(wasSuccessful);
        when(objectsConstructor.constructObjectFromRecipe(constructionRecipe)).thenReturn(wasSuccessful = false);
        when(logger.log(eq(LoggingType.WARNING), anyString())).thenReturn(null);
        when(productionLineMover.moveProductionLine(eq(MovingDirection.TO_SCRAN))).thenThrow(Exception.class);
        when(logger.log(eq(LoggingType.ERROR), anyString())).thenReturn(null);

        controller.execute();

        verify(constructionRecipeCreator, times(1)).getConstructionRecipe();
        verify(constructionRecipeCreator, times(1)).getNumberOfElementsToProduce();
        verify(objectsConstructor, times(1)).constructObjectFromRecipe(constructionRecipe);
        verify(logger, times(1)).log(eq(LoggingType.WARNING), anyString());
        verify(logger, times(1)).log(eq(LoggingType.ERROR), anyString());
        verify(productionLineMover, times(1)).moveProductionLine(MovingDirection.FORWARD);
        verify(productionLineMover, times(1)).moveProductionLine(MovingDirection.TO_SCRAN);
    }
}
