package com.example;

import java.io.Serializable;
import java.util.Comparator;

public class Employee implements Comparable<Employee>, Comparator<Employee>, Serializable {
    private final String name;
    private final int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }
   
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public int compare(Employee emp1, Employee emp2) {
        return Integer.compare(emp1.getId(), emp2.getId());
    }
}
