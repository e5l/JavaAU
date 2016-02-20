package ru.spbau.hw1.Trie;

import static org.junit.Assert.assertEquals;


public class TrieImplTest {
    private TrieImpl tmp;

    @org.junit.Before
    public void runBefore() throws Exception {
        tmp = new TrieImpl();
        tmp.add("Hello");
        tmp.add("World");
        tmp.add("Hell");
        tmp.add("Hello world");
    }

    @org.junit.Test
    public void testAdd() throws Exception {
        tmp.add("Some");
        tmp.add("Some");
        tmp.add("Some");
        tmp.add("some");
        tmp.add("some");
        tmp.add("some");

        tmp.add("creepy");
        tmp.add("creepy word");
        tmp.add("creepy word to");
        tmp.add("creepy word to add");

        assert tmp.contains("Some");
        assert tmp.contains("some");
        assert tmp.contains("creepy");
        assert !tmp.contains("casdasd");
        assert !tmp.contains("Some ");

    }

    @org.junit.Test
    public void testContains() throws Exception {
        assert tmp.contains("Hell");
        assert tmp.contains("Hello world");
        assert !tmp.contains("Hi");
        assert !tmp.contains("Hello ");
    }

    @org.junit.Test
    public void testRemove() throws Exception {
        assert tmp.remove("Hello");
        assert !tmp.remove("sjfklsfd");

        assert tmp.contains("Hell");
        assert !tmp.contains("Hi");
        assert !tmp.contains("Hello");
    }

    @org.junit.Test
    public void testSize() throws Exception {
        assertEquals(tmp.size(), 4);
    }

    @org.junit.Test
    public void testHowManyStartsWithPrefix() throws Exception {
        assertEquals(tmp.howManyStartsWithPrefix("He"), 3);
        assertEquals(tmp.howManyStartsWithPrefix("Hello"), 2);
        assertEquals(tmp.howManyStartsWithPrefix("Hello "), 1);
        assertEquals(tmp.howManyStartsWithPrefix("Hello world"), 1);
    }

}