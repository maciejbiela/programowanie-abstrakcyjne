package io.github.maciejbiela.homework5;

import java.util.Optional;

public class SortedDeque<T extends Comparable> {
    private final int bucketSize;
    private int numberOfBuckets;
    private int totalSize;
    private int uniqueSize;

    private Bucket<T> firstBucket;

    public SortedDeque() {
        this.bucketSize = 0;
    }

    public SortedDeque(int bucketSize) {
        this.bucketSize = bucketSize;
        this.firstBucket = new Bucket<>(bucketSize);
        this.numberOfBuckets = 1;
    }

    public boolean isEmpty() {
        return this.totalSize == 0;
    }

    public int totalSize() {
        return this.totalSize;
    }

    public int uniqueSize() {
        return this.uniqueSize;
    }

    public int capacity() {
        return this.numberOfBuckets * this.bucketSize;
    }

    public void reserve(int capacity) {
        this.numberOfBuckets = (int) Math.ceil(((double) capacity) / this.bucketSize);
        this.firstBucket.createAdditionalBuckets(this.numberOfBuckets - 1);
    }

    public int numberOfBuckets() {
        return this.numberOfBuckets;
    }

    public void insert(T valueToAdd) {
        InsertReturnInformation<T> insertReturnInformation = this.firstBucket.insert(valueToAdd);
        updateStatisticsGiven(insertReturnInformation);
    }

    public T get(int index) {
        return this.firstBucket.get(index);
    }

    public T back() {
        return this.firstBucket.get(this.uniqueSize - 1);
    }

    private void updateStatisticsGiven(InsertReturnInformation<T> insertReturnInformation) {
        updateFirstBucket(insertReturnInformation);
        updateTotalSize(insertReturnInformation);
        updateUniqueSize(insertReturnInformation);
        updateNumberOfBuckets(insertReturnInformation);
    }

    private void updateNumberOfBuckets(InsertReturnInformation<T> insertReturnInformation) {
        if (insertReturnInformation.getIncrementNumberOfBuckets()) {
            this.numberOfBuckets++;
        }
    }

    private void updateUniqueSize(InsertReturnInformation<T> insertReturnInformation) {
        if (insertReturnInformation.getIncrementUniqueSize()) {
            this.uniqueSize++;
        }
    }

    private void updateTotalSize(InsertReturnInformation<T> insertReturnInformation) {
        if (insertReturnInformation.getIncrementTotalSize()) {
            this.totalSize++;
        }
    }

    private void updateFirstBucket(InsertReturnInformation<T> insertReturnInformation) {
        Optional<Bucket<T>> possiblyNewFirstBucket = insertReturnInformation.getPossiblyNewFirstBucket();
        if (possiblyNewFirstBucket.isPresent()) {
            this.firstBucket = possiblyNewFirstBucket.get();
        }
    }
}
