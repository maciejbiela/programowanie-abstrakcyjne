package io.github.maciejbiela.homework5;

import java.util.Optional;

public class SortedDeque<T extends Comparable> {
    private final int bucketSize;
    private int capacity;
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
    }

    public boolean isEmpty() {
        if (this.firstBucket == null) {
            return true;
        }
        return this.firstBucket.isEmpty();
    }

    public int totalSize() {
        if (this.firstBucket == null) {
            return 0;
        }
        return this.firstBucket.getTotalSize();
    }

    public int uniqueSize() {
        if (this.firstBucket == null) {
            return 0;
        }
        return this.firstBucket.getUniqueSize();
    }

    public int capacity() {
        if (this.firstBucket == null) {
            return 0;
        }
        this.numberOfBuckets = this.firstBucket.getNumberOfBuckets();
        return this.numberOfBuckets * this.bucketSize;
    }

    public void reserve(int capacity) {
        this.numberOfBuckets = (int) Math.ceil(((double) capacity) / this.bucketSize);
        this.capacity = this.numberOfBuckets * this.bucketSize;
        this.firstBucket.createAdditionalBuckets(this.numberOfBuckets - 1);
    }

    public int numberOfBuckets() {
        return this.numberOfBuckets;
    }

    public void insert(T valueToAdd) {
        InsertReturnInformation<T> insertReturnInformation = this.firstBucket.insert(valueToAdd);
        Optional<Bucket<T>> possiblyNewFirstBucket = insertReturnInformation.getPossiblyNewFirstBucket();
        if (possiblyNewFirstBucket.isPresent()) {
            this.firstBucket = possiblyNewFirstBucket.get();
        }
    }

    public T get(int index) {
        return this.firstBucket.get(index);
    }

    public T back() {
        return this.firstBucket.get(this.uniqueSize() - 1);
    }
}
