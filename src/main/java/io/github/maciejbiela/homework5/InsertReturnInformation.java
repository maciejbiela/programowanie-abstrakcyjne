package io.github.maciejbiela.homework5;

import java.util.Optional;

public class InsertReturnInformation<T extends Comparable> {
    private Optional<Bucket<T>> possiblyNewFirstBucket;
    private int newTotalSize;
    private int newUniqueSize;
    private int newNumberOfBuckets;
    private int newCapacity;

    private InsertReturnInformation(Optional<Bucket<T>> possiblyNewFirstBucket, int newTotalSize, int newUniqueSize, int newNumberOfBuckets, int newCapacity) {
        this.possiblyNewFirstBucket = possiblyNewFirstBucket;
        this.newTotalSize = newTotalSize;
        this.newUniqueSize = newUniqueSize;
        this.newNumberOfBuckets = newNumberOfBuckets;
        this.newCapacity = newCapacity;
    }

    public Optional<Bucket<T>> getPossiblyNewFirstBucket() {
        return possiblyNewFirstBucket;
    }

    public int getNewTotalSize() {
        return newTotalSize;
    }

    public int getNewUniqueSize() {
        return newUniqueSize;
    }

    public int getNewNumberOfBuckets() {
        return newNumberOfBuckets;
    }

    public int getNewCapacity() {
        return newCapacity;
    }

    public static class Builder<T extends Comparable> {
        private Optional<Bucket<T>> possiblyNewFirstBucket;
        private int newTotalSize;
        private int newUniqueSize;
        private int newNumberOfBuckets;
        private int newCapacity;

        public void withPossiblyNewFirstBucket(Optional<Bucket<T>> possiblyNewFirstBucket) {
            this.possiblyNewFirstBucket = possiblyNewFirstBucket;
        }

        public void withNewTotalSize(int newTotalSize) {
            this.newTotalSize = newTotalSize;
        }

        public void withNewUniqueSize(int newUniqueSize) {
            this.newUniqueSize = newUniqueSize;
        }

        public void withNewNumberOfBuckets(int newNumberOfBuckets) {
            this.newNumberOfBuckets = newNumberOfBuckets;
        }

        public void withNewCapacity(int newCapacity) {
            this.newCapacity = newCapacity;
        }

        public InsertReturnInformation<T> build() {
            return new InsertReturnInformation<>(this.possiblyNewFirstBucket,
                    this.newTotalSize,
                    this.newUniqueSize,
                    this.newNumberOfBuckets,
                    this.newCapacity);
        }
    }
}
