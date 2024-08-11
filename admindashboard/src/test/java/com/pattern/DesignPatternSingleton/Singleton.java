package com.pattern.DesignPatternSingleton;

public class Singleton {
    // Private static variable to hold the single instance
    private static Singleton instance;

    // Private constructor to prevent instantiation
    private Singleton() {
    }

    // Public static method to provide access to the single instance
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Other methods of the Singleton class
    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}

