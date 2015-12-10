package io.github.maciejbiela.homework4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Composition<T> implements Executable<T>, Iterable<Executable<T>> {
    List<Executable<T>> functions = new ArrayList<>();

    @SafeVarargs
    public Composition(Executable<T>... functions) {
        Collections.addAll(this.functions, functions);
    }

    public Composition(List<Executable<T>> functions) {
        this.functions = functions;
    }

    public void add(Executable<T> function) {
        this.functions.add(function);
    }

    @Override
    public Iterator<Executable<T>> iterator() {
        return this.functions.iterator();
    }

    @Override
    public T execute(T argument) {
        if (functions.size() == 0) {
            return null;
        }
        return executeRecursively(argument, 0);
    }

    private T executeRecursively(T argument, int i) {
        if (i == functions.size() - 1) {
            return functions.get(i).execute(argument);
        } else {
            return executeRecursively(functions.get(i).execute(argument), i + 1);
        }
    }
}