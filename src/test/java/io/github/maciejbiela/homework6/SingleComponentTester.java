package io.github.maciejbiela.homework6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SingleComponentTester {
    @Test
    public void testLogger() {
        class Logs {
            int loggedErrors = 0;
            int loggedWarnings = 0;
            int loggedInformation = 0;
        }
        Logs logs = new Logs();

        Logger mock = mock(Logger.class);

        when(mock.log(eq(LoggingType.ERROR), anyString())).thenAnswer(invocation -> {
            logs.loggedErrors++;
            return null;
        });
        when(mock.log(eq(LoggingType.WARNING), anyString())).thenAnswer(invocation -> {
            logs.loggedWarnings++;
            return null;
        });
        when(mock.log(eq(LoggingType.INFO), anyString())).thenAnswer(invocation -> {
            logs.loggedInformation++;
            return null;
        });

        mock.log(LoggingType.ERROR, "seriousError");
        mock.log(LoggingType.ERROR, "seriousError2");
        mock.log(LoggingType.WARNING, "wrn");
        mock.log(LoggingType.INFO, "successfully done");
        mock.log(LoggingType.INFO, "done");
        mock.log(LoggingType.INFO, "done again");

        assertEquals(2, logs.loggedErrors);
        assertEquals(1, logs.loggedWarnings);
        assertEquals(3, logs.loggedInformation);

        verify(mock, atLeastOnce()).log(eq(LoggingType.ERROR), anyString());
        verify(mock, times(1)).log(eq(LoggingType.WARNING), anyString());
        verify(mock, times(3)).log(eq(LoggingType.INFO), anyString());
    }

    @Test
    public void testProductionLineMover() {
        ProductionLineMover mock = mock(ProductionLineMover.class);

        mock.moveProductionLine(MovingDirection.FORWARD);
        mock.moveProductionLine(MovingDirection.TO_SCRAN);
        mock.moveProductionLine(MovingDirection.FORWARD);
        mock.moveProductionLine(MovingDirection.FORWARD);

        verify(mock, times(3)).moveProductionLine(MovingDirection.FORWARD);
        verify(mock, times(1)).moveProductionLine(MovingDirection.TO_SCRAN);
        verify(mock, never()).moveProductionLine(MovingDirection.BACK);
    }


    @Test
    public void testConstructionRecipe() {
        ConstructionRecipe mock = mock(ConstructionRecipe.class);
        final String nameOfObject = "Fiat 126P";

        when(mock.getNameOfObject()).thenReturn(nameOfObject);

        assertEquals(nameOfObject, mock.getNameOfObject());
    }

    @Test
    public void testIConstructionRecipeCreator() {
        ConstructionRecipe mock = mock(ConstructionRecipe.class);
        final String nameOfObject = "Fiat 126P";
        when(mock.getNameOfObject()).thenReturn(nameOfObject);

        final int numberOfObjectsToProduce = 10;
        ConstructionRecipeCreator mockCreator = mock(ConstructionRecipeCreator.class);
        when(mockCreator.getConstructionRecipe()).thenReturn(mock);
        when(mockCreator.getNumberOfElementsToProduce()).thenReturn(numberOfObjectsToProduce);

        assertEquals(numberOfObjectsToProduce, mockCreator.getNumberOfElementsToProduce());
        assertEquals(nameOfObject, mockCreator.getConstructionRecipe().getNameOfObject());
    }

    @Test
    public void testObjectConstructor() {
        ConstructionRecipe mock = mock(ConstructionRecipe.class);
        ObjectsConstructor mockObjectConstructor = mock(ObjectsConstructor.class);
        when(mockObjectConstructor.constructObjectFromRecipe(mock)).thenReturn(true);

        assertEquals(true, mockObjectConstructor.constructObjectFromRecipe(mock));
        verify(mockObjectConstructor, times(1)).constructObjectFromRecipe(mock);
    }
}