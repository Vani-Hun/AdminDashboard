package com.example;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        // Tạo một HashMap để lưu trữ cặp key-value
        Map<String, Integer> hashMap = new HashMap<>();

        // Thêm các phần tử vào HashMap
        hashMap.put("John", 25);
        hashMap.put("Mary", 30);
        hashMap.put("Peter", 22);
        hashMap.put("Alice", 28);
        hashMap.put("Bob", 27);

        // In ra các phần tử trong HashMap
        System.out.println("Các phần tử trong HashMap:");
        System.out.println("hashMap:" + hashMap);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

