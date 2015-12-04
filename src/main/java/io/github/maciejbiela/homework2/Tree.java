package io.github.maciejbiela.homework2;

import java.util.*;

public class Tree<T> implements Iterable<T> {
    private T value;
    private EnumeratorOrder order;
    private List<Tree<T>> children;

    private Color color;

    private enum Color {
        WHITE,
        GRAY,
    }

    public Tree(T value, EnumeratorOrder order) {
        this.value = value;
        this.order = order;
        children = new ArrayList<>();
    }

    public Tree(T value, EnumeratorOrder order, Iterable<T> children) {
        this.value = value;
        this.order = order;
        this.children = new ArrayList<>();
        addAllChildren(children);
    }

    public void setOrder(EnumeratorOrder order) {
        this.order = order;
        setDescendantOrder(order);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public T getValue() {
        return value;
    }

    public EnumeratorOrder getOrder() {
        return order;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public Color getColor() {
        return color;
    }

    public void add(Tree<T> child) {
        child.setOrder(order);
        child.setDescendantOrder(order);
        children.add(child);
    }

    public void add(T child) {
        Tree<T> childTree = new Tree<>(child, order);
        children.add(childTree);
    }

    public void add(Iterable<T> children) {
        for (T child : children) {
            add(child);
        }
    }

    public List<T> asList() {
        List<T> list = new ArrayList<>();
        switch (order) {
            case BreadthFirstSearch:
                asListUsingBFS(list);
                break;
            case DepthFirstSearch:
                asListUsingDFS(list);
                break;
        }
        return list;
    }

    private void asListUsingDFS(List<T> list) {
        list.add(value);
        for (Tree<T> child : children) {
            list.addAll(child.asList());
        }
    }

    private void asListUsingBFS(List<T> list) {
        clearColor();
        Stack<Tree<T>> stack = new Stack<>();
        list.add(this.value);
        this.setColor(Color.GRAY);
        stack.push(this);
        while (!stack.isEmpty()) {
            Tree<T> tree = stack.pop();
            for (Tree<T> child : tree.getChildren()) {
                stack.push(child);
                if (child.getColor().equals(Color.WHITE)) {
                    list.add(child.getValue());
                    child.setColor(Color.GRAY);
                }
            }
        }
    }

    private void addAllChildren(Iterable<T> children) {
        for (T child : children) {
            add(child);
        }
    }

    private void setDescendantOrder(EnumeratorOrder order) {
        Stack<Tree<T>> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Tree<T> tree = stack.pop();
            for (Tree<T> child : tree.getChildren()) {
                stack.push(child);
                child.setOrder(order);
            }
        }
    }

    private void clearColor() {
        Stack<Tree<T>> stack = new Stack<>();
        this.setColor(Color.WHITE);
        stack.push(this);
        while (!stack.isEmpty()) {
            Tree<T> tree = stack.pop();
            for (Tree<T> child : tree.getChildren()) {
                stack.push(child);
                child.setColor(Color.WHITE);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return asList().iterator();
    }

    public T[] toArray(T[] array) {
        return asList().toArray(array);
    }

    public T get(int index) {
        return asList().get(index);
    }

    public int size() {
        return asList().size();
    }
}
