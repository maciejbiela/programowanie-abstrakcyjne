package io.github.maciejbiela.homework3;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TripleListTest {
    @Test
    public void testEmptyListCreation() {
        TripleList<Integer> tripleList = new TripleList<>();
        assertEquals(0, tripleList.size());

        assertNull(tripleList.getPreviousElement());
        assertNull(tripleList.getMiddleElement());
        assertNull(tripleList.getNextElement());
    }

    @Test
    public void testAddingSingleElement() {
        TripleList<Integer> tripleList = new TripleList<>();
        final int value = 4;
        tripleList.add(value);
        assertEquals(1, tripleList.size());
        assertEquals(Integer.valueOf(value), tripleList.getValue());

        assertNull(tripleList.getPreviousElement());
        assertNull(tripleList.getMiddleElement());
        assertNull(tripleList.getNextElement());
    }

    @Test
    public void testAddingTwoElements() {
        TripleList<Integer> tripleList = new TripleList<>();
        final Integer value1 = 4;
        final Integer value2 = -9;
        tripleList.add(value1);
        tripleList.add(value2);
        assertEquals(2, tripleList.size());
        // checking values
        assertEquals(value1, tripleList.getValue());
        assertEquals(value2, tripleList.getMiddleElement().getValue());
        assertEquals(tripleList.getValue(), tripleList.getMiddleElement().getMiddleElement().getValue());
        // checking neighbour nodes of first element
        assertNull(tripleList.getPreviousElement());
        assertNotNull(tripleList.getMiddleElement());
        assertNull(tripleList.getNextElement());
        // checking neighbour nodes of second element
        assertNull(tripleList.getMiddleElement().getPreviousElement());
        assertNull(tripleList.getMiddleElement().getNextElement());
    }

    @Test
    public void testAddingTreeElements() {
        TripleList<Integer> tripleList = new TripleList<>();
        final Integer value1 = 4;
        final Integer value2 = -9;
        final Integer value3 = 47;
        tripleList.add(value1);
        tripleList.add(value2);
        tripleList.add(value3);
        assertEquals(3, tripleList.size());
        // checking values
        assertEquals(value1, tripleList.getValue());
        assertEquals(value2, tripleList.getMiddleElement().getValue());
        assertEquals(value3, tripleList.getNextElement().getValue());
        // checking neighbour nodes of first element
        assertNull(tripleList.getPreviousElement());
        assertNotNull(tripleList.getMiddleElement());
        assertNotNull(tripleList.getNextElement());
        // checking neighbour nodes of second element
        assertNull(tripleList.getMiddleElement().getPreviousElement());
        assertNotNull(tripleList.getMiddleElement().getMiddleElement());
        assertNull(tripleList.getMiddleElement().getNextElement());
        // checking neighbour nodes of third/last element
        assertNotNull(tripleList.getNextElement().getPreviousElement());
        assertNull(tripleList.getNextElement().getMiddleElement());
        assertNull(tripleList.getNextElement().getNextElement());
        // checking values
        assertEquals(value1, tripleList.getValue());
        assertEquals(value2, tripleList.getMiddleElement().getValue());
        assertEquals(value3, tripleList.getNextElement().getValue());
    }

    @Test
    public void testAddingFiveElements() {
        TripleList<Integer> tripleList = new TripleList<>();
        final Integer value1 = 1;
        final Integer value2 = 2;
        final Integer value3 = 3;
        final Integer value4 = 4;
        final Integer value5 = 5;
        tripleList.add(value1);
        tripleList.add(value2);
        tripleList.add(value3);
        tripleList.add(value4);
        tripleList.add(value5);
        assertEquals(5, tripleList.size());
        // checking values
        assertEquals(value1, tripleList.getValue());
        assertEquals(value2, tripleList.getMiddleElement().getValue());
        assertEquals(value3, tripleList.getNextElement().getValue());
        assertEquals(value4, tripleList.getNextElement().getMiddleElement().getValue());
        assertEquals(value5, tripleList.getNextElement().getNextElement().getValue());
    }

    @Test
    public void testListIterator() {
        Double[] values = {1.1, 3.14, 6.13, 9.99999, 99.001};
        TripleList<Double> tripleList = new TripleList<>();
        int i;
        for (i = 0; i < values.length; i++) {
            tripleList.add(values[i]);
        }
        i = 0;
        for (Double d : tripleList) {
            assertEquals(values[i++], d);
        }
    }

    @Test
    public void testListIterator2() {
        Double[] values = {1.1, 3.14, 6.13, 9.99999, 99.001};
        TripleList<Double> tripleList = new TripleList<>();
        int i;
        for (i = 0; i < values.length; i++) {
            tripleList.add(values[i]);
        }
        i = 0;
        Iterator<Double> it = tripleList.iterator();
        while (it.hasNext()) {
            assertEquals(values[i++], it.next());
        }
    }

    @Test
    public void testIfNoCycle() {
        /** Initialization of the TripleList **/
        final int NUMBER_OF_ELEMENTS = 100;
        TripleList<Integer> tripleList = new TripleList<>();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            tripleList.add(i);
        }
        /** Created 2 TripleLists, first jumps every single element,
         another every two elements, in out case every two elements means every NextElement**/
        TripleList<Integer> tripleListEverySingleNode = tripleList;
        TripleList<Integer> tripleListEveryTwoNodes = tripleList.getNextElement();
    }
}