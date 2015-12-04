package io.github.maciejbiela.homework3;

public class TripleListNode<T> {
    private T value;

    private TripleListNode<T> previousElement;
    private TripleListNode<T> middleElement;
    private TripleListNode<T> nextElement;

    public TripleListNode(T value) {
        this.value = value;
    }

    public TripleListNode(T value, TripleListNode<T> previousElement, TripleListNode<T> middleElement, TripleListNode<T> nextElement) {
        this.value = value;
        this.previousElement = previousElement;
        this.middleElement = middleElement;
        this.nextElement = nextElement;
    }

    public T getValue() {
        return value;
    }

    public TripleListNode<T> getPreviousElement() {
        return previousElement;
    }

    public TripleListNode<T> getMiddleElement() {
        return middleElement;
    }

    public TripleListNode<T> getNextElement() {
        return nextElement;
    }

    public void setNextElement(TripleListNode<T> nextElement) {
        this.nextElement = nextElement;
    }

    public void setMiddleElement(TripleListNode<T> middleElement) {
        this.middleElement = middleElement;
    }
}
