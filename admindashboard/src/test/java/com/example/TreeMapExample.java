package com.example;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {
        // Tạo một TreeMap để lưu trữ cặp key-value
        Map<String, Integer> treeMap = new TreeMap<>();

        // Thêm các phần tử vào TreeMap
        treeMap.put("John", 25);
        treeMap.put("Mary", 30);
        treeMap.put("Peter", 22);
        treeMap.put("Alice", 28);
        treeMap.put("Bob", 27);

        // In ra các phần tử trong TreeMap
        System.out.println("Các phần tử trong TreeMap:");
        System.out.println("treeMap:" + treeMap);

        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

