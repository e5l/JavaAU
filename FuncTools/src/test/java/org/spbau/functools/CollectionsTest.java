package org.spbau.functools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {

    @Test
    public void map() {
        ArrayList<Integer> input = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> expectedHashes = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);
            expected.add(i * i);
            expectedHashes.add(input.get(i).hashCode());
        }

        Function1<Integer, Integer> sqr = a -> a * a;
        Function1<Object, Integer> hash = Object::hashCode;

        List<Integer> result = Collections.map(sqr, input);
        List<Integer> resultHashes = Collections.map(hash, input);

        assertEquals(expected, result);
        assertEquals(expectedHashes, resultHashes);
    }

    @Test
    public void filter() {
        ArrayList<Integer> input = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Number> tensExpected = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            input.add(-i);
            input.add(i);

            expected.add(-i);

            if (i < 10) {
                expected.add(i);
            }

            if (i == 10) {
                tensExpected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a < 10;
        Predicate<Number> isTen = a -> a.equals(10);

        List<Integer> result = Collections.filter(greater, input);
        List<Integer> tens = Collections.filter(isTen, input);

        assertEquals(expected, result);
        assertEquals(tensExpected, tens);
    }

    @Test
    public void takeWhile() {
        ArrayList<Integer> input = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);

            if (i < 10) {
                expected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a < 10;
        Predicate<Number> notTen = a -> !a.equals(10);

        List<Integer> result = Collections.takeWhile(greater, input);
        List<Integer> resultTens = Collections.takeWhile(notTen, input);

        assertEquals(expected, result);
        assertEquals(expected, resultTens);
    }

    @Test
    public void takeUnless() {
        ArrayList<Integer> input = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            input.add(i);

            if (i < 10) {
                expected.add(i);
            }
        }

        Predicate<Integer> greater = a -> a >= 10;
        Predicate<Number> notTen = a -> !a.equals(10);

        List<Integer> result = Collections.takeUnless(greater, input);
        List<Integer> resultTens = Collections.takeWhile(notTen, input);

        assertEquals(expected, result);
        assertEquals(expected, resultTens);
    }

    @Test
    public void foldr() {
        ArrayList<Integer> input = new ArrayList<>();
        int hashResult = 0;

        for (int i = 0; i < 100; ++i) {
            input.add(i);
            hashResult += input.get(i).hashCode();
        }

        Function2<Integer, Integer, Integer> diff = (a, b) -> a - b;
        Function2<Object, Integer, Integer> hashSum = (a, b) -> a.hashCode() + b;

        assertEquals(new Integer(-50), Collections.foldr(diff, 0, input));
        assertEquals(new Integer(hashResult), Collections.foldr(hashSum, 0, input));
    }

    @Test
    public void foldl() {
        ArrayList<Integer> input = new ArrayList<>();
        int hashResult = 0;

        for (int i = 0; i < 100; ++i) {
            input.add(i);
            hashResult += input.get(i).hashCode();
        }

        Function2<Integer, Integer, Integer> diff = (a, b) -> a - b;
        Function2<Integer, Object, Integer> hashSum = (a, b) -> a + b.hashCode();

        assertEquals(new Integer(-4950), Collections.foldl(diff, 0, input));
        assertEquals(new Integer(hashResult), Collections.foldl(hashSum, 0, input));
    }

}