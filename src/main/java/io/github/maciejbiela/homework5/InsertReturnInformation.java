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

    public boolean getIncrementTotalSize() {
        return incrementTotalSize;
    }

    public boolean getIncrementUniqueSize() {
        return incrementUniqueSize;
    }

    public boolean getIncrementNumberOfBuckets() {
        return incrementNumberOfBuckets;
    }

    public static class Builder<T extends Comparable> {
        private Optional<Bucket<T>> possiblyNewFirstBucket;
        private boolean incrementTotalSizeBy;
        private boolean incrementUniqueSizeBy;
        private boolean incrementNumberOfBucketsBy;

        public void withPossiblyNewFirstBucket(Optional<Bucket<T>> possiblyNewFirstBucket) {
            this.possiblyNewFirstBucket = possiblyNewFirstBucket;
        }

        public void withIncrementTotalSizeBy(boolean incrementTotalSizeBy) {
            this.incrementTotalSizeBy = incrementTotalSizeBy;
        }

        public void withIncrementUniqueSizeBy(boolean incrementUniqueSizeBy) {
            this.incrementUniqueSizeBy = incrementUniqueSizeBy;
        }

        public void withIncrementNumberOfBucketsBy(boolean incrementNumberOfBucketsBy) {
            this.incrementNumberOfBucketsBy = incrementNumberOfBucketsBy;
        }

        public InsertReturnInformation<T> build() {
            return new InsertReturnInformation<>(this.possiblyNewFirstBucket,
                    this.incrementTotalSizeBy,
                    this.incrementUniqueSizeBy,
                    this.incrementNumberOfBucketsBy);
        }
    }
}
