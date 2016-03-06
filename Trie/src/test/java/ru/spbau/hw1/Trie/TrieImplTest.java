package ru.spbau.hw1.Trie;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TrieImplTest {
    private Trie tmp;

    @Before
    public void runBefore() throws Exception {
        tmp = new TrieImpl();
        tmp.add("Hello");
        tmp.add("World");
        tmp.add("Hell");
        tmp.add("Hello world");
    }

    @Test
    public void testAdd() throws Exception {
        assertTrue(tmp.add("Some"));
        assertFalse(tmp.add("Some"));
        assertFalse(tmp.add("Some"));
        assertTrue(tmp.add("some"));
        assertFalse(tmp.add("some"));
        assertFalse(tmp.add("some"));

        assertTrue(tmp.add("creepy"));
        assertTrue(tmp.add("creepy word"));
        assertTrue(tmp.add("creepy word to"));
        assertTrue(tmp.add("creepy word to add"));

        assertTrue(tmp.contains("Some"));
        assertTrue(tmp.contains("some"));
        assertTrue(tmp.contains("creepy"));
        assertFalse(tmp.contains("casdasd"));
        assertFalse(tmp.contains("Some "));

    }

    @Test
    public void testSimple() {
        Trie trie = new TrieImpl();

        assertTrue(trie.add("abc"));
        assertTrue(trie.contains("abc"));
        assertEquals(1, trie.size());
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
    }

    @Test
    public void testContains() throws Exception {
        assertTrue(tmp.contains("Hell"));
        assertTrue(tmp.contains("Hello world"));
        assertFalse(tmp.contains("Hi"));
        assertFalse(tmp.contains("Hello "));
    }

    @Test
    public void testRemove() throws Exception {
        assertEquals(4, tmp.size());

        assertTrue(tmp.remove("Hello"));
        assertEquals(3, tmp.size());

        assertFalse(tmp.remove("sjfklsfd"));
        assertEquals(3, tmp.size());

        assertTrue(tmp.contains("Hell"));
        assertFalse(tmp.contains("Hi"));
        assertFalse(tmp.contains("Hello"));

        Trie set = new TrieImpl();
        String s = "string";

        assertTrue(set.add(s));
        assertEquals(1, set.size());

        assertTrue(set.remove(s));
        assertEquals(0, set.size());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(4, tmp.size());
    }

    @Test
    public void testHowManyStartsWithPrefix() throws Exception {
        assertEquals(3, tmp.howManyStartsWithPrefix("He"));
        assertEquals(2, tmp.howManyStartsWithPrefix("Hello"));
        assertEquals(1, tmp.howManyStartsWithPrefix("Hello "));
        assertEquals(1, tmp.howManyStartsWithPrefix("Hello world"));
    }

}