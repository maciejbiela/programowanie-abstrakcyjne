package io.github.maciejbiela.homework2;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TreeTest {
    @Test
    public void enumerationValidation() {
        Tree<Integer> subtree = new Tree<>(5, EnumeratorOrder.BreadthFirstSearch, Arrays.asList(1, 2));
        Tree<Integer> tree = new Tree<>(7, EnumeratorOrder.BreadthFirstSearch);
        tree.add(subtree);
        tree.add(Arrays.asList(new Integer[]{10, 15}));
        assertEquals(Integer.valueOf(10), StreamSupport.stream(tree.spliterator(), false)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .get());
        Integer[] bfs = {7, 5, 10, 15, 1, 2};
        Integer[] maybeBfs = tree.toArray(new Integer[tree.size()]);
        assertEquals(bfs.length, maybeBfs.length);
        for (int i = 0; i < bfs.length; i++) {
            assertEquals(bfs[i], maybeBfs[i]);
        }
        tree.setOrder(EnumeratorOrder.DepthFirstSearch);
        assertEquals(Integer.valueOf(2), StreamSupport.stream(tree.spliterator(), false)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .get());
        Integer[] dfs = {7, 5, 1, 2, 10, 15};
        Integer[] maybeDfs = tree.toArray(new Integer[tree.size()]);
        assertEquals(dfs.length, maybeDfs.length);
        for (int i = 0; i < dfs.length; i++) {
            assertEquals(dfs[i], maybeDfs[i]);
        }
    }

    @Test
    public void enumerateWithNoChildren() {
        Tree<Integer> tree = new Tree<>(7, EnumeratorOrder.DepthFirstSearch);
        assertEquals(Integer.valueOf(7), tree.get(0));
        tree.setOrder(EnumeratorOrder.BreadthFirstSearch);
        assertEquals(Integer.valueOf(7), tree.get(0));
        Tree<Integer> subtree = new Tree<>(5, EnumeratorOrder.BreadthFirstSearch);
        tree.add(subtree);
        assertEquals(Integer.valueOf(5), tree.get(tree.size() - 1));
        tree.setOrder(EnumeratorOrder.DepthFirstSearch);
        assertEquals(Integer.valueOf(5), tree.get(tree.size() - 1));
    }

    @Test
    public void orderPropertyValidation() {
        Tree<Integer> subtree = new Tree<>(5, EnumeratorOrder.DepthFirstSearch, Arrays.asList(1, 2));
        Tree<Integer> tree = new Tree<>(7, EnumeratorOrder.BreadthFirstSearch);
        tree.add(subtree);
        assertEquals(EnumeratorOrder.BreadthFirstSearch, subtree.getOrder());
        for (Tree<Integer> child : subtree.getChildren()) {
            assertEquals(EnumeratorOrder.BreadthFirstSearch, child.getOrder());
        }
        subtree.add(3);
        assertEquals(EnumeratorOrder.BreadthFirstSearch, subtree.getChildren()
                .stream()
                .filter(t -> t.getValue() == 3)
                .findFirst()
                .get()
                .getOrder());
        tree.setOrder(EnumeratorOrder.DepthFirstSearch);
        subtree.add(4);
        assertEquals(EnumeratorOrder.DepthFirstSearch, subtree.getChildren()
                .stream()
                .filter(t -> t.getValue() == 4)
                .findFirst()
                .get()
                .getOrder());
        try {
            tree.setOrder(EnumeratorOrder.valueOf("SomeOtherEnumeratorOrder"));
            fail("Unknown order type defined");
        } catch (IllegalArgumentException e) {
            // Correct exception thrown, everything's fine!
        } catch (Exception e) {
            fail("Invalid exception type");
        }
    }
}