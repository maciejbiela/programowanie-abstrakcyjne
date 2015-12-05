package io.github.maciejbiela.homework3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public TripleList(T[] values) {
        for (T value : values) {
            this.add(value);
        }
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

    public int size() {
        return size;
    }

    public void add(T value) {
        if (isAddingFirstElement()) {
            addFirstElement(value);
        } else if (isAddingSecondElement()) {
            addSecondElement(value);
        } else if (isAddingUpperRowElement()) {
            addUpperRowElement(value);
        } else {
            addLowerRowElement(value);
        }
        size++;
    }

    private void addLowerRowElement(T value) {
        TripleListNode<T> upperNode = lastNode.getMiddleElement();
        TripleListNode<T> newNode = new TripleListNode<>(value, upperNode, null, null);
        upperNode.setNextElement(newNode);
        lastNode = newNode;
    }

    private void addUpperRowElement(T value) {
        TripleListNode<T> newNode = new TripleListNode<>(value, null, lastNode, null);
        lastNode.setMiddleElement(newNode);
        lastNode = newNode;
    }

    private boolean isAddingUpperRowElement() {
        return lastNode.getMiddleElement() == null;
    }

    private void addSecondElement(T value) {
        TripleListNode<T> newNode = new TripleListNode<>(value, null, firstNode, null);
        firstNode.setMiddleElement(newNode);
        lastNode = newNode;
    }

    private boolean isAddingSecondElement() {
        return firstNode == lastNode;
    }

    private void addFirstElement(T value) {
        firstNode = lastNode = new TripleListNode<>(value);
    }

    private boolean isAddingFirstElement() {
        return lastNode == null;
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
            return currentNode != null;
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
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (T value : this) {
            list.add(value);
        }
        return list;
    }

    public T[] toArray(T[] array) {
        return toList().toArray(array);
    }

    private class Explorer {
        public TripleListNode<T> getPreviousElement() {
            return isStillAValidNode() ? firstNode.getPreviousElement() : null;
        }

        public TripleListNode<T> getMiddleElement() {
            return isStillAValidNode() ? firstNode.getMiddleElement() : null;
        }

        public TripleListNode<T> getNextElement() {
            return isStillAValidNode() ? firstNode.getNextElement() : null;
        }

        private boolean isStillAValidNode() {
            return firstNode != null;
        }
    }
}


