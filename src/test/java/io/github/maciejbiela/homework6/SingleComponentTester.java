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
}