package io.github.maciejbiela.homework3;

import java.util.Iterator;

public class TripleList<T> implements Iterable<T> {
    private TripleListNode<T> firstNode;
    private TripleListNode<T> lastNode;

    private int size;

    public TripleList() {
        size = 0;
        firstNode = lastNode = null;
    }

    public T getValue() {
        return firstNode.getValue();
    }

    public TripleListNode<T> getPreviousElement() {
        if (firstNode != null) {
            return firstNode.getPreviousElement();
        }
        return null;
    }

    public TripleListNode<T> getMiddleElement() {
        if (firstNode != null) {
            return firstNode.getMiddleElement();
        }
        return null;
    }

    public TripleListNode<T> getNextElement() {
        if (firstNode != null) {
            return firstNode.getNextElement();
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void add(T value) {
        if (lastNode == null) {
            firstNode = lastNode = new TripleListNode<>(value);
        } else if (firstNode == lastNode) {
            TripleListNode<T> newNode = new TripleListNode<>(value, null, firstNode, null);
            firstNode.setMiddleElement(newNode);
            lastNode = newNode;
        } else if (lastNode.getMiddleElement() == null) {
            TripleListNode<T> newNode = new TripleListNode<>(value, null, lastNode, null);
            lastNode.setMiddleElement(newNode);
            lastNode = newNode;
        } else {
            TripleListNode<T> upperNode = lastNode.getMiddleElement();
            TripleListNode<T> newNode = new TripleListNode<>(value, upperNode, null, null);
            upperNode.setNextElement(newNode);
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new TripleListIterator();
    }

    private class TripleListIterator implements Iterator<T> {
        private TripleListNode<T> currentNode;
        private boolean isUp;

        public TripleListIterator() {
            currentNode = firstNode;
            isUp = true;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null && currentNode != lastNode;
        }

        @Override
        public T next() {
            T next = currentNode.getValue();
            if (isUp) {
                currentNode = currentNode.getMiddleElement();
            } else {
                currentNode = currentNode.getMiddleElement().getNextElement();
            }
            isUp = !isUp;
            return next;
        }
    }
}
