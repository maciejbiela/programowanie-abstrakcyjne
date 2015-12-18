package io.github.maciejbiela.homework5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bucket<T extends Comparable> {
    private Bucket<T> previousBucket;
    private Bucket<T> nextBucket;

    private List<Item<T>> items;

    private final int bucketSize;
    private int totalSize;
    private int uniqueSize;

    private T smallest;
    private T largest;

    public Bucket(int bucketSize) {
        this.bucketSize = bucketSize;
        this.totalSize = 0;
        this.uniqueSize = 0;
        this.items = new ArrayList<>(bucketSize);
    }

    public InsertReturnInformation<T> insert(T valueToAdd) {
        InsertReturnInformation.Builder<T> builder = new InsertReturnInformation.Builder<>();
        Optional<Bucket<T>> possiblyNewFirstBucket = Optional.empty();
        if (shouldAppendNewBucketToTheLeft(valueToAdd)) {
           possiblyNewFirstBucket = Optional.of(appendNewBucketToTheLeftWithValue(valueToAdd));
        } else if (shouldAppendNewBucketToTheRight(valueToAdd)) {
            appendNewBucketToTheRightWithValue(valueToAdd);
        } else if (shouldBucketToTheRightHandleIt(valueToAdd)) {
            this.nextBucket.insert(valueToAdd);
        } else if (shouldNewBucketBeCreatedBetween(valueToAdd)) {
            createNewBucketBetween(valueToAdd);
        } else {
            insertInCurrentBucket(valueToAdd);
        }
        builder.withPossiblyNewFirstBucket(possiblyNewFirstBucket);
        return builder.build();
    }

    private void insertInCurrentBucket(Item<T> item) {
        this.items.add(item);
        this.uniqueSize++;
        this.totalSize += item.getCount();
        this.smallest = this.items.get(0).getValue();
        this.largest = this.items.get(this.uniqueSize - 1).getValue();
    }

    private void insertInCurrentBucket(T valueToAdd) {
        Optional<Item<T>> maybeItem = this.items
                .stream()
                .filter(item -> valueToAdd.equals(item.getValue()))
                .findFirst();
        if (maybeItem.isPresent()) {
            maybeItem.get().incrementCount();
        } else {
            if (isCurrentBucketFull()) {
                splitCurrentBucket(valueToAdd);
            } else {
                this.items.add(this.uniqueSize, new Item<>(valueToAdd));
                this.uniqueSize++;
                this.items.sort(Item::compareTo);
                this.smallest = this.items.get(0).getValue();
                this.largest = this.items.get(this.uniqueSize - 1).getValue();
                this.totalSize++;
            }
        }
    }

    private void createNewBucketBetween(T valueToBeAdded) {
        Bucket<T> tmp = this.nextBucket;
        Bucket<T> newBucket = new Bucket<>(this.bucketSize);
        this.nextBucket = newBucket;
        newBucket.previousBucket = this;
        newBucket.nextBucket = tmp;
        tmp.previousBucket = newBucket;
        newBucket.insertInCurrentBucket(valueToBeAdded);
    }

    private boolean shouldNewBucketBeCreatedBetween(T valueToAdd) {
        if (this.nextBucket == null) {
            return false;
        }
        return this.isCurrentBucketFull() && valueToAdd.compareTo(this.largest) > 0 && this.nextBucket.isCurrentBucketFull() && valueToAdd.compareTo(this.nextBucket.smallest) < 0;
    }

    private boolean shouldBucketToTheRightHandleIt(T valueToAdd) {
        if (this.nextBucket == null) {
            return false;
        }
        return (valueToAdd.compareTo(this.largest) > 0 && !this.nextBucket.isCurrentBucketFull());
    }

    private void splitCurrentBucket(T valueToAdd) {
        int indexOfFirstLargerItem = 0;
        while (this.items.get(indexOfFirstLargerItem).getValue().compareTo(valueToAdd) < 0) {
            indexOfFirstLargerItem++;
        }
        Bucket<T> tmp = this.nextBucket;
        Bucket<T> newBucketBetween = new Bucket<>(this.bucketSize);

        this.nextBucket = newBucketBetween;
        newBucketBetween.previousBucket = this;
        newBucketBetween.nextBucket = tmp;
        tmp.previousBucket = newBucketBetween;

        newBucketBetween.insertInCurrentBucket(valueToAdd);
        for (int i = indexOfFirstLargerItem; i < this.uniqueSize; i++) {
            this.uniqueSize--;
            this.totalSize -= this.items.get(i).getCount();
            newBucketBetween.insertInCurrentBucket(this.items.get(i));
        }
        this.largest = this.items.get(this.uniqueSize - 1).getValue();
    }

    private void appendNewBucketToTheRightWithValue(T valueToAdd) {
        Bucket<T> newBucket = new Bucket<>(this.bucketSize);
        newBucket.previousBucket = this;
        this.nextBucket = newBucket;
        newBucket.insert(valueToAdd);
    }

    private boolean shouldAppendNewBucketToTheRight(T valueToBeAdded) {
        return isCurrentBucketFull() &&
                valueToBeAdded.compareTo(this.largest) > 0 &&
                this.nextBucket == null;
    }

    private Bucket<T> appendNewBucketToTheLeftWithValue(T valueToAdd) {
        Bucket<T> newBucket = new Bucket<>(this.bucketSize);
        newBucket.nextBucket = this;
        this.previousBucket = newBucket;
        newBucket.insertInCurrentBucket(valueToAdd);
        return newBucket;
    }

    private boolean shouldAppendNewBucketToTheLeft(T valueToBeAdded) {
        return isCurrentBucketFull() &&
                valueToBeAdded.compareTo(this.smallest) < 0 &&
                this.previousBucket == null;
    }

    private boolean isCurrentBucketFull() {
        return this.uniqueSize == this.bucketSize;
    }

    public int getTotalSize() {
        int totalSize = 0;
        Bucket<T> currentBucket = this;
        while (currentBucket != null) {
            totalSize += currentBucket.totalSize;
            currentBucket = currentBucket.nextBucket;
        }
        return totalSize;
    }

    public int getUniqueSize() {
        int uniqueSize = 0;
        Bucket<T> currentBucket = this;
        while (currentBucket != null) {
            uniqueSize += currentBucket.uniqueSize;
            currentBucket = currentBucket.nextBucket;
        }
        return uniqueSize;
    }

    public boolean isEmpty() {
        return this.totalSize == 0;
    }

    public T get(int index) {
        if (index < this.uniqueSize) {
            return this.items.get(index).getValue();
        } else {
            Bucket<T> nextBucket = this.nextBucket;
            index -= this.uniqueSize;
            while (nextBucket.uniqueSize <= index) {
                index -= nextBucket.uniqueSize;
                nextBucket = nextBucket.nextBucket;
            }
            return nextBucket.items.get(index).getValue();
        }
    }

    public int getNumberOfBuckets() {
        int numberOfBuckets = 0;
        Bucket<T> currentBucket = this;
        while (currentBucket != null) {
            numberOfBuckets++;
            currentBucket = currentBucket.nextBucket;
        }
        return numberOfBuckets;
    }

    public void createAdditionalBuckets(int count) {
        Bucket<T> currentBucket = this;
        for (int i = 0; i < count; i++) {
            Bucket<T> newBucket = new Bucket<>(this.bucketSize);
            currentBucket.nextBucket = newBucket;
            newBucket.previousBucket = currentBucket;
            currentBucket = newBucket;
        }
    }
}
