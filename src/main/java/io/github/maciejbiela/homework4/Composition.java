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
        T result =  argument;
        for (Executable<T> function : functions) {
            result = function.execute(result);
        }
        return result;
    }
}