package io.github.maciejbiela.homework4;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CompositionTest {
    @Test
    public void implementInterfaces() {
        Composition<Double> comp = new Composition<>();
        assertTrue(comp instanceof Executable);
        assertTrue(comp instanceof Iterable);
    }

    @Test
    public void composeOneFunction() {
        Executable<Integer> linearFunction = (Integer x) -> 2 * x + 10;

        Composition<Integer> comp = new Composition<>(linearFunction);
        assertEquals(10, (int) comp.execute(0));
        assertEquals(14, (int) comp.execute(2));
        assertEquals(20, (int) comp.execute(5));
    }

    @Test
    public void composeTwoFunctions() {
        Executable<Integer> linearFunction = (Integer x) -> 2 * x + 5;
        Executable<Integer> quadraticFunction = (Integer x) -> x * x;

        Composition<Integer> comp = new Composition<>(linearFunction, quadraticFunction);
        assertEquals(25, (int) comp.execute(0));
        assertEquals(81, (int) comp.execute(2));
        assertEquals(225, (int) comp.execute(5));

        comp = new Composition<>(quadraticFunction, linearFunction);
        assertEquals(5, (int) comp.execute(0));
        assertEquals(13, (int) comp.execute(2));
        assertEquals(55, (int) comp.execute(5));

        comp = new Composition<>(linearFunction, linearFunction);
        assertEquals(15, (int) comp.execute(0));
        assertEquals(23, (int) comp.execute(2));
        assertEquals(35, (int) comp.execute(5));

        comp = new Composition<>(quadraticFunction, quadraticFunction);
        assertEquals(0, (int) comp.execute(0));
        assertEquals(16, (int) comp.execute(2));
        assertEquals(625, (int) comp.execute(5));
    }

    @Test
    public void composeThreeFunctions() {
        Executable<Integer> identity = (Integer x) -> x;
        Executable<Integer> linearFunction = (Integer x) -> x + 2;
        Executable<Integer> cubicFunction = (Integer x) -> x * x * x - 1;
        ArrayList<Executable<Integer>> functions = new ArrayList<>();
        functions.add(identity);
        functions.add(linearFunction);
        functions.add(cubicFunction);
        Composition<Integer> compose = new Composition<>(functions);
        assertEquals(7, (int) compose.execute(0));
        assertEquals(63, (int) compose.execute(2));
        assertEquals(26, (int) compose.execute(1));
    }

    @Test
    public void addingFunctionToComposition() {
        Executable<Integer> identity = (Integer x) -> x;

        Composition<Integer> comp = new Composition<>(identity);
        assertEquals(0, (int) comp.execute(0));
        assertEquals(2, (int) comp.execute(2));

        Executable<Integer> linearFunction = (Integer x) -> x + 2;

        comp.add(linearFunction);
        assertEquals(2, (int) comp.execute(0));
        assertEquals(4, (int) comp.execute(2));

        Executable<Integer> cubicFunction = (Integer x) -> x * x * x;

        comp.add(cubicFunction);
        assertEquals(8, (int) comp.execute(0));
        assertEquals(64, (int) comp.execute(2));
    }

    @Test
    public void compositionOfCompositions() {
        Executable<Integer> linearFunction = (Integer x) -> x + 2;
        Executable<Integer> quadraticFunction = (Integer x) -> 2 * x * x;
        Composition<Integer> comp1 = new Composition<>(linearFunction, quadraticFunction);
        Composition<Integer> comp2 = new Composition<>(quadraticFunction, linearFunction);
        Composition<Integer> compOfComps = new Composition<>(comp1, comp2);
        assertEquals(130, (int) compOfComps.execute(0));
        assertEquals(650, (int) compOfComps.execute(1));
        assertEquals(2050, (int) compOfComps.execute(2));
    }

    @Test
    public void checkIterator() {
        Executable<Integer> identity = (Integer x) -> x;
        Executable<Integer> linearFunction = (Integer x) -> 3 * x - 2;
        Executable<Integer> quadraticFunction = (Integer x) -> 2 * x * x - 5;
        Executable<Integer> cubicFunction = (Integer x) -> x * x * x + x * x + x + 1;

        ArrayList<Executable<Integer>> functions = new ArrayList<>();
        functions.add(identity);
        functions.add(linearFunction);
        functions.add(quadraticFunction);
        functions.add(cubicFunction);
        Composition<Integer> comp = new Composition<>(functions);
        int tab0[] = new int[]{0, -2, -5, 1};
        int tab1[] = new int[]{1, 1, -3, 4};
        int tab2[] = new int[]{2, 4, 3, 15};
        int i = 0;
        for (Executable<Integer> f : comp) {
            assertEquals((int) f.execute(0), tab0[i]);
            assertEquals((int) f.execute(1), tab1[i]);
            assertEquals((int) f.execute(2), tab2[i]);
            i++;
        }
    }

}