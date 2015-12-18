package io.github.maciejbiela.homework5;

import java.util.Optional;

public class InsertReturnInformation<T extends Comparable> {
    private Optional<Bucket<T>> possiblyNewFirstBucket;
    private boolean incrementTotalSize;
    private boolean incrementUniqueSize;
    private boolean incrementNumberOfBuckets;

    private InsertReturnInformation(Optional<Bucket<T>> possiblyNewFirstBucket,
                                    boolean incrementTotalSize,
                                    boolean incrementUniqueSize,
                                    boolean incrementNumberOfBuckets) {
        this.possiblyNewFirstBucket = possiblyNewFirstBucket;
        this.incrementTotalSize = incrementTotalSize;
        this.incrementUniqueSize = incrementUniqueSize;
        this.incrementNumberOfBuckets = incrementNumberOfBuckets;
    }

    public Optional<Bucket<T>> getPossiblyNewFirstBucket() {
        return possiblyNewFirstBucket;
    }

    public boolean shouldIncrementTotalSize() {
        return incrementTotalSize;
    }

    public boolean shouldIncrementUniqueSize() {
        return incrementUniqueSize;
    }

    public boolean shouldIncrementNumberOfBuckets() {
        return incrementNumberOfBuckets;
    }

    public static class Builder<T extends Comparable> {
        private Optional<Bucket<T>> possiblyNewFirstBucket;
        private boolean incrementTotalSize;
        private boolean incrementUniqueSize;
        private boolean incrementNumberOfBuckets;

        public void withPossiblyNewFirstBucket(Optional<Bucket<T>> possiblyNewFirstBucket) {
            this.possiblyNewFirstBucket = possiblyNewFirstBucket;
        }

        public void withIncrementTotalSize(boolean incrementTotalSize) {
            this.incrementTotalSize = incrementTotalSize;
        }

        public void withIncrementUniqueSize(boolean incrementUniqueSize) {
            this.incrementUniqueSize = incrementUniqueSize;
        }

        public void withIncrementNumberOfBuckets(boolean incrementNumberOfBuckets) {
            this.incrementNumberOfBuckets = incrementNumberOfBuckets;
        }

        public InsertReturnInformation<T> build() {
            return new InsertReturnInformation<>(this.possiblyNewFirstBucket,
                    this.incrementTotalSize,
                    this.incrementUniqueSize,
                    this.incrementNumberOfBuckets);
        }
    }
}
