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

    public TripleList(TripleListNode<T> firstNode, TripleListNode<T> lastNode, int size) {
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.size = size;
    }

    public T getValue() {
        return firstNode.getValue();
    }

    public TripleList<T> getPreviousElement() {
        Explorer explorer = new Explorer();
        TripleListNode<T> nextNode = explorer.getPreviousElement();
        return getQueriedTripleList(nextNode);
    }

    public TripleList<T> getMiddleElement() {
        Explorer explorer = new Explorer();
        TripleListNode<T> nextNode = explorer.getMiddleElement();
        return getQueriedTripleList(nextNode);
    }

    public TripleList<T> getNextElement() {
        Explorer explorer = new Explorer();
        TripleListNode<T> nextNode = explorer.getNextElement();
        return getQueriedTripleList(nextNode);
    }

    private TripleList<T> getQueriedTripleList(TripleListNode<T> nextNode) {
        if (nextNode == null) {
            return null;
        } else {
            return new TripleList<>(nextNode, lastNode, size);
        }
    }

    private class Explorer {
        public TripleListNode<T> getPreviousElement() {
            return firstNode != null ? firstNode.getPreviousElement() : null;
        }

        public TripleListNode<T> getMiddleElement() {
            return firstNode != null ? firstNode.getMiddleElement() : null;
        }

        public TripleListNode<T> getNextElement() {
            return firstNode != null ? firstNode.getNextElement() : null;
        }
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


