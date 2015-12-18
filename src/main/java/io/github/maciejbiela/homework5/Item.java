package io.github.maciejbiela.homework5;

public class Item<T extends Comparable> implements Comparable<Item<T>> {
    private T value;
    private int count;

    public Item() {
    }

    public Item(T value) {
        this.value = value;
        this.count = 1;
    }

    public T getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }

    @Override
    public int compareTo(Item<T> o) {
        return this.value.compareTo(o.value);
    }
}
