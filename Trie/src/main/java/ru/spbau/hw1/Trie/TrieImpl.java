package ru.spbau.hw1.Trie;

import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;


public class TrieImpl implements Trie, StreamSerializable {
    private final HashMap<Character, TrieImpl> dict = new HashMap<>();
    private int size = 0;
    private boolean terminal = false;

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

    @Override
    public void serialize(OutputStream out) throws IOException {
        JSONObject j = new JSONObject(this);
        out.write(j.toString().getBytes());
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder builder = new StringBuilder();
        String str = reader.readLine();
        while (str != null) {
            builder.append(str);
            str = reader.readLine();
        }

        JSONObject state = new JSONObject(builder.toString());
        fromJSON(state);
    }

    public int getSize() {
        return size;
    }

    public HashMap<Character, TrieImpl> getDict() {
        return dict;
    }

    public boolean getTerminal() {
        return terminal;
    }

    private void fromJSON(JSONObject json) {
        terminal = json.getBoolean("terminal");
        size = json.getInt("size");
        dict.clear();

        JSONObject kids = json.getJSONObject("dict");
        Iterator<String> i = kids.keys();

        while (i.hasNext()) {
            String key = i.next();

            TrieImpl kid = new TrieImpl();
            kid.fromJSON(kids.getJSONObject(key));

            dict.put(key.charAt(0), kid);
        }

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
