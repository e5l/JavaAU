package ru.spbau.hw1.Trie;

import java.util.HashMap;

public class TrieImpl implements Trie {
    private final HashMap<Character, TrieImpl> dict = new HashMap<>();
    private int size = 0;
    private boolean terminal = false;

    public TrieImpl() {
    }

    @Override
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }

        TrieImpl tmp = this;

        for (int i = 0; i < element.length(); i++) {
            char elem = element.charAt(i);
            tmp.size++;

            if (!tmp.dict.containsKey(elem)) {
                tmp.dict.put(elem, new TrieImpl());
            }

            tmp = tmp.dict.get(elem);
        }

        tmp.size++;
        tmp.terminal = true;
        return true;
    }

    @Override
    public boolean contains(String element) {
        TrieImpl tmp = find(element);
        return tmp != null && tmp.terminal;
    }

    @Override
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }

        TrieImpl tmp = this;
        for (int i = 0; i < element.length(); i++) {
            char elem = element.charAt(i);
            tmp.size--;
            tmp = tmp.dict.get(elem);
        }

        tmp.size--;
        tmp.terminal = false;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        TrieImpl tmp = find(prefix);
        return tmp == null ? 0 : tmp.size();
    }

    private TrieImpl find(String element) {
        TrieImpl tmp = this;

        for (int i = 0; i < element.length(); i++) {
            char elem = element.charAt(i);
            if (!tmp.dict.containsKey(elem)) {
                return null;
            }

            tmp = tmp.dict.get(elem);
        }

        return tmp;
    }
}
