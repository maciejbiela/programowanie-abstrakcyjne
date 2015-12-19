package io.github.maciejbiela.homework5;

import org.junit.Test;

import java.util.Arrays;
import java.util.Vector;

import static org.junit.Assert.*;

public class SortedDequeTest {
    @Test
    public void testConstructionOfEmptyContainer() {
        SortedDeque<Integer> sortedDeque = new SortedDeque<>();
        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.totalSize());
        assertEquals(0, sortedDeque.uniqueSize());
        assertEquals(0, sortedDeque.capacity());
    }

    @Test
    public void testReserve() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        final int capacityThatIWant = SIZE_OF_BUCKET * 10 + 1;
        sortedDeque.reserve(capacityThatIWant);

        final int expectedNumberOfBuckets = (int) Math.ceil(((double) capacityThatIWant) / SIZE_OF_BUCKET);
        assertEquals(expectedNumberOfBuckets, sortedDeque.numberOfBuckets());

        final int expectedCapacity = expectedNumberOfBuckets * SIZE_OF_BUCKET;
        assertEquals(expectedCapacity, sortedDeque.capacity());

        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.totalSize());
        assertEquals(0, sortedDeque.uniqueSize());
    }

    @Test
    public void testReserveWithSmallerNewCapacity() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        final int capacityThatIWant = SIZE_OF_BUCKET * 10;
        sortedDeque.reserve(capacityThatIWant);

        final int newCapacityThatIWant = SIZE_OF_BUCKET * 5;
        sortedDeque.reserve(newCapacityThatIWant);

        assertEquals(newCapacityThatIWant / SIZE_OF_BUCKET, sortedDeque.numberOfBuckets());
        assertEquals(newCapacityThatIWant, sortedDeque.capacity());

        assertTrue(sortedDeque.isEmpty());
        assertEquals(0, sortedDeque.totalSize());
        assertEquals(0, sortedDeque.uniqueSize());
    }

    @Test
    public void testSimpleAddingIntoSingleBucket() throws Exception {
        Vector<Integer> valuesToAdd = new Vector<>(Arrays.asList(2, 0, 9, 1));

        final int SIZE_OF_BUCKET = 10;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);
        valuesToAdd.forEach(sortedDeque::insert);

        valuesToAdd.sort(Integer::compareTo);
        for (int i = 0; i < valuesToAdd.size(); i++) {
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));
        }

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.totalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.uniqueSize());

        assertEquals(SIZE_OF_BUCKET, sortedDeque.capacity());
        assertEquals(1, sortedDeque.numberOfBuckets());
    }

    @Test
    public void testAddingSmallestValueWhenFirstBucketIsFull() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        Vector<Integer> valuesToAdd = new Vector<>(Arrays.asList(2, 1, 9));

        valuesToAdd.forEach(sortedDeque::insert);

        valuesToAdd.sort(Integer::compareTo);

        for (int i = 0; i < valuesToAdd.size(); i++) {
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));
        }

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.totalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.uniqueSize());

        final Integer valueSmallerThanPreviousValueToAdd = 0;
        sortedDeque.insert(valueSmallerThanPreviousValueToAdd);
        assertEquals(valueSmallerThanPreviousValueToAdd, sortedDeque.get(0));

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.totalSize());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.uniqueSize());

        assertEquals(2 * SIZE_OF_BUCKET, sortedDeque.capacity());
        assertEquals(2, sortedDeque.numberOfBuckets());
    }

    @Test
    public void testAddingGreaterValueWhenFirstBucketIsFull() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        Vector<Integer> valuesToAdd = new Vector<>(Arrays.asList(2, 1, 9));

        valuesToAdd.forEach(sortedDeque::insert);

        valuesToAdd.sort(Comparable::compareTo);

        for (int i = 0; i < valuesToAdd.size(); i++) {
            assertEquals(valuesToAdd.get(i), sortedDeque.get(i));
        }

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size(), sortedDeque.totalSize());
        assertEquals(valuesToAdd.size(), sortedDeque.uniqueSize());

        assertEquals(SIZE_OF_BUCKET, sortedDeque.capacity());
        assertEquals(1, sortedDeque.numberOfBuckets());

        final int valueGreaterThanPreviousValuesToAdd = 100;
        sortedDeque.insert(valueGreaterThanPreviousValuesToAdd);

        assertTrue(sortedDeque.back() == valueGreaterThanPreviousValuesToAdd);

        assertFalse(sortedDeque.isEmpty());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.totalSize());
        assertEquals(valuesToAdd.size() + 1, sortedDeque.uniqueSize());

        assertEquals(2 * SIZE_OF_BUCKET, sortedDeque.capacity());
        assertEquals(2, sortedDeque.numberOfBuckets());
    }

    @Test
    public void testAddingValueWhichShouldBeAddedBetweenTwoFullBuckets() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Integer> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        Vector<Integer> valuesToAddToFirstBucket = new Vector<>(Arrays.asList(1, 2, 3));
        valuesToAddToFirstBucket.forEach(sortedDeque::insert);

        Vector<Integer> valuesToAddToSecondBucket = new Vector<>(Arrays.asList(5, 6, 7));
        valuesToAddToSecondBucket.forEach(sortedDeque::insert);

        final int valueThatShouldBeAddedBetweenTwoFullBuckets = 4;
        sortedDeque.insert(valueThatShouldBeAddedBetweenTwoFullBuckets);

        final int expectedNumberOfElements = valuesToAddToFirstBucket.size() + 1 + valuesToAddToSecondBucket.size();
        assertEquals(expectedNumberOfElements, sortedDeque.totalSize());
        assertEquals(expectedNumberOfElements, sortedDeque.uniqueSize());

        assertEquals(3 * SIZE_OF_BUCKET, sortedDeque.capacity());
        assertEquals(3, sortedDeque.numberOfBuckets());
    }

    @Test
    public void testAddingValueWhichShouldBeAddedBetweenTwoValuesInFullBucketAfterWhichIsAnotherFullBucket() {
        final int SIZE_OF_BUCKET = 3;
        SortedDeque<Double> sortedDeque = new SortedDeque<>(SIZE_OF_BUCKET);

        Vector<Double> valuesToAddToFirstBucket = new Vector<>(Arrays.asList(1., 2., 3.));
        valuesToAddToFirstBucket.forEach(sortedDeque::insert);

        Vector<Double> valuesToAddToSecondBucket = new Vector<>(Arrays.asList(5., 6., 7.));
        valuesToAddToSecondBucket.forEach(sortedDeque::insert);

        Double valueThatShouldBeAddedBetweenTwoFullBuckets = 2.78;
        sortedDeque.insert(valueThatShouldBeAddedBetweenTwoFullBuckets);

        Vector<Double> allValues = new Vector<>();
        allValues.addAll(valuesToAddToFirstBucket);
        allValues.addAll(valuesToAddToSecondBucket);
        allValues.add(valueThatShouldBeAddedBetweenTwoFullBuckets);
        allValues.sort(Comparable::compareTo);

        for (int i = 0; i < allValues.size(); i++) {
            assertEquals(allValues.get(i), sortedDeque.get(i));
        }

        final int expectedTotalNumberOfElements = valuesToAddToFirstBucket.size() + 1 + valuesToAddToSecondBucket.size();
        assertEquals(expectedTotalNumberOfElements, sortedDeque.totalSize());
        assertEquals(expectedTotalNumberOfElements, sortedDeque.uniqueSize());
    }
}