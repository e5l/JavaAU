package org.spbau.functools;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {

    @Test
    public void or() throws Exception {
        Predicate<Integer> positive = a -> a > 0;
        Predicate<Integer> negative = a -> a < 0;

        assertTrue(positive.or(negative).apply(2));
        assertTrue(positive.or(negative).apply(-2));
        assertFalse(positive.or(negative).apply(0));

        Predicate<Integer> Fail = arg -> {
            // shouldn't happen
            assertTrue(false);
            return false;
        };

        assertTrue(positive.or(Fail).apply(1));

        assertTrue(negative.or(Predicate.ALWAYS_TRUE).apply(-1));
    }

    @Test
    public void and() throws Exception {
        Predicate<Integer> positive = a -> a > 0;
        Predicate<Integer> negative = a -> a < 0;

        assertFalse(positive.and(negative).apply(2));
        assertFalse(positive.and(negative).apply(-2));
        assertFalse(positive.and(negative).apply(0));

        Predicate<Integer> fail = arg -> {
            fail();
            return false;
        };

        assertFalse(negative.and(fail).apply(1));
        assertFalse(negative.and(Predicate.ALWAYS_FALSE).apply(-1));
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> positive = a -> a > 0;
        Predicate<Integer> negative = a -> a < 0;

        assertTrue(positive.and(negative).not().apply(2));
        assertTrue(positive.and(negative).not().apply(-2));
        assertTrue(positive.and(negative).not().apply(0));

        assertTrue(Predicate.ALWAYS_FALSE.not().apply(0));
    }
}