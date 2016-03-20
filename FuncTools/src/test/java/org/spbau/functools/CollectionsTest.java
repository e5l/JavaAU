package org.spbau.functools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {

    @Test
    public void map() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);
            expected.add(i * i);
        }

        Function1<Integer, Integer> sqr = a -> a * a;
        List<Integer> result = Collections.map(sqr, input);

        assertEquals(expected, result);
    }

    @Test
    public void filter() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(-i);
            input.add(i);

            expected.add(-i);

            if (i < 10) {
                expected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a < 10;
        List<Integer> result = Collections.filter(greater, input);

        assertEquals(expected, result);
    }

    @Test
    public void takeWhile() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);

            if (i < 10) {
                expected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a < 10;
        List<Integer> result = Collections.takeWhile(greater, input);

        assertEquals(expected, result);
    }

    @Test
    public void takeUnless() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);

            if (i < 10) {
                expected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a >= 10;
        List<Integer> result = Collections.takeUnless(greater, input);

        assertEquals(expected, result);
    }

    @Test
    public void foldr() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);
        }

        Function2<Integer, Integer, Integer> diff = (a, b) -> a - b;
        assertEquals(new Integer(-50), Collections.foldr(diff, 0, input));
    }

    @Test
    public void foldl() throws Exception {
        ArrayList<Integer> input = new ArrayList<Integer>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);
        }

        Function2<Integer, Integer, Integer> diff = (a, b) -> a - b;
        assertEquals(new Integer(-4950), Collections.foldl(diff, 0, input));
    }

}