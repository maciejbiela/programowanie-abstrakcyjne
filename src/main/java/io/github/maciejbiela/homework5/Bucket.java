package io.github.maciejbiela.homework5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bucket<T extends Comparable> {
    private Bucket<T> previousBucket;
    private Bucket<T> nextBucket;

    private List<Item<T>> items;

    private final int bucketSize;
    private int uniqueSize;

    private T smallest;
    private T largest;

    public Bucket(int bucketSize) {
        this.bucketSize = bucketSize;
        this.uniqueSize = 0;
        this.items = new ArrayList<>(bucketSize);
    }

    public InsertReturnInformation<T> insertReturningInformationViaBuilder(T valueToAdd) {
        InsertReturnInformation.Builder builder = new InsertReturnInformation.Builder();
        return insertReturningInformationViaBuilder(valueToAdd, builder);
    }

    private InsertReturnInformation<T> insertReturningInformationViaBuilder
            (T valueToAdd, InsertReturnInformation.Builder builder) {

        if (shouldCreateNewFirstBucket(valueToAdd)) {
            createNewFirstBucketWithValue(valueToAdd, builder);
        } else if (shouldAppendNewBucketToTheRight(valueToAdd)) {
            appendNewBucketToTheRightWithValue(valueToAdd, builder);
        } else if (shouldBucketToTheRightHandleIt(valueToAdd)) {
            this.nextBucket.insertReturningInformationViaBuilder(valueToAdd, builder);
        } else if (shouldNewBucketBeCreatedBetween(valueToAdd)) {
            createNewBucketBetween(valueToAdd, builder);
        } else {
            insertInCurrentBucket(valueToAdd, builder);
        }
        return builder.build();
    }

    private boolean shouldCreateNewFirstBucket(T valueToBeAdded) {
        return isCurrentBucketFull() &&
                valueToBeAdded.compareTo(this.smallest) < 0 &&
                this.previousBucket == null;
    }

    private void createNewFirstBucketWithValue(T valueToAdd, InsertReturnInformation.Builder builder) {
        Bucket<T> newFirstBucket = new Bucket<>(this.bucketSize);
        newFirstBucket.nextBucket = this;
        this.previousBucket = newFirstBucket;
        newFirstBucket.insertInCurrentBucket(valueToAdd, builder);

        builder.withIncrementNumberOfBuckets(true);
        builder.withPossiblyNewFirstBucket(Optional.of(newFirstBucket));
    }

    private boolean shouldAppendNewBucketToTheRight(T valueToBeAdded) {
        return isCurrentBucketFull() &&
                valueToBeAdded.compareTo(this.largest) > 0 &&
                this.nextBucket == null;
    }

    private void appendNewBucketToTheRightWithValue(T valueToAdd, InsertReturnInformation.Builder builder) {
        Bucket<T> newBucket = new Bucket<>(this.bucketSize);
        builder.withIncrementNumberOfBuckets(true);
        newBucket.previousBucket = this;
        this.nextBucket = newBucket;
        newBucket.insertReturningInformationViaBuilder(valueToAdd, builder);
    }

    private boolean shouldBucketToTheRightHandleIt(T valueToAdd) {
        if (this.nextBucket == null) {
            return false;
        }
        return (valueToAdd.compareTo(this.largest) > 0 && !this.nextBucket.isCurrentBucketFull());
    }

    private boolean shouldNewBucketBeCreatedBetween(T valueToAdd) {
        if (this.nextBucket == null) {
            return false;
        }
        return this.isCurrentBucketFull() && valueToAdd.compareTo(this.largest) > 0 && this.nextBucket.isCurrentBucketFull() && valueToAdd.compareTo(this.nextBucket.smallest) < 0;
    }

    private void createNewBucketBetween(T valueToBeAdded, InsertReturnInformation.Builder builder) {
        Bucket<T> tmp = this.nextBucket;
        Bucket<T> newBucket = new Bucket<>(this.bucketSize);
        this.nextBucket = newBucket;
        newBucket.previousBucket = this;
        newBucket.nextBucket = tmp;
        tmp.previousBucket = newBucket;
        builder.withIncrementNumberOfBuckets(true);
        newBucket.insertInCurrentBucket(valueToBeAdded, builder);
    }

    private void insertInCurrentBucket(T valueToAdd, InsertReturnInformation.Builder builder) {
        Optional<Item<T>> searchResult = findValueInCurrentBucket(valueToAdd);
        if (searchResult.isPresent()) {
            insertWhenValueIsAlreadyInTheBucket(searchResult, builder);
        } else {
            insertWhenValueIsNotPresentInTheBucket(valueToAdd, builder);
        }
    }

    private Optional<Item<T>> findValueInCurrentBucket(T valueToAdd) {
        return this.items
                .stream()
                .filter(item -> valueToAdd.equals(item.getValue()))
                .findFirst();
    }

    private void insertWhenValueIsAlreadyInTheBucket(Optional<Item<T>> maybeItem, InsertReturnInformation.Builder builder) {
        maybeItem.get().incrementCount();
        builder.withIncrementTotalSize(true);
    }

    private void insertWhenValueIsNotPresentInTheBucket(T valueToAdd, InsertReturnInformation.Builder builder) {
        if (isCurrentBucketFull()) {
            splitCurrentBucket(valueToAdd, builder);
        } else {
            addValueToCurrentBucket(valueToAdd, builder);
        }
    }

    private void splitCurrentBucket(T valueToAdd, InsertReturnInformation.Builder builder) {
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

        builder.withIncrementNumberOfBuckets(true);

        newBucketBetween.insertInCurrentBucket(valueToAdd, builder);
        for (int i = indexOfFirstLargerItem; i < this.uniqueSize; i++) {
            this.uniqueSize--;
            newBucketBetween.moveExistingItem(this.items.get(i));
        }
        this.largest = this.items.get(this.uniqueSize - 1).getValue();
    }

    private void addValueToCurrentBucket(T valueToAdd, InsertReturnInformation.Builder builder) {
        this.items.add(this.uniqueSize, new Item<>(valueToAdd));
        this.uniqueSize++;
        this.items.sort(Item::compareTo);
        this.smallest = this.items.get(0).getValue();
        this.largest = this.items.get(this.uniqueSize - 1).getValue();
        builder.withIncrementTotalSize(true);
        builder.withIncrementUniqueSize(true);
    }

    private void moveExistingItem(Item<T> item) {
        this.items.add(item);
        this.uniqueSize++;
        this.smallest = this.items.get(0).getValue();
        this.largest = this.items.get(this.uniqueSize - 1).getValue();
    }

    private boolean isCurrentBucketFull() {
        return this.uniqueSize == this.bucketSize;
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
