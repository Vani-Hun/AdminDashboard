package com.example;

import java.util.LinkedList;

public class SimpleHashTable {
    private static final int TABLE_SIZE = 10;
    private final LinkedList<Entry>[] table;

    public SimpleHashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public static void main(String[] args) {
        SimpleHashTable hashTable = new SimpleHashTable();
        hashTable.put("name", "Alice");
        hashTable.put("age", "30");
        System.out.println(hashTable.get("name")); // In ra "Alice"
        System.out.println(hashTable.get("age")); // In ra "30"
    }

    private int hash(String key) {
        return key.hashCode() % TABLE_SIZE;
    }

    public void put(String key, String value) {
        int index = hash(key);
        LinkedList<Entry> list = table[index];
        for (Entry entry : list) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        list.add(new Entry(key, value));
    }

    public String get(String key) {
        int index = hash(key);
        LinkedList<Entry> list = table[index];
        for (Entry entry : list) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    private static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}

