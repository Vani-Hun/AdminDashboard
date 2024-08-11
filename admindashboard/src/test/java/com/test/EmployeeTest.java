package com.test;

import com.example.Employee;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    static {
        System.out.println("Chạy trước hàm main");
    }

    @Test
    void testComparable() {
        Employee emp1 = new Employee("Alice", 1);
        Employee emp2 = new Employee("Bob", 2);

        assertEquals(-1, emp1.compareTo(emp2)); // emp1 < emp2
        assertEquals(1, emp2.compareTo(emp1));  // emp2 > emp1
        assertEquals(0, emp1.compareTo(emp1));  // emp1 == emp1
    }

    @Test
    void testComparator() {
        Employee emp1 = new Employee("Alice", 1);
        Employee emp2 = new Employee("Bob", 2);

        assertEquals(-1, emp1.compare(emp1, emp2)); // emp1 < emp2
        assertEquals(1, emp1.compare(emp2, emp1));  // emp2 > emp1
        assertEquals(0, emp1.compare(emp1, emp1));  // emp1 == emp1
    }

    @Test
    void writeObjectToFile() throws IOException {
        Employee emp = new Employee("Alice", 1);

        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/templates/Employee.txt");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(emp);
            // Thêm kiểm tra thành công nếu cần
        }
    }
}
