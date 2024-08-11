package com.example;

public class FinallyExample {
    public static void main(String[] args) {
        try {
            int result = 10 / 0; // Lỗi chia cho 0, sẽ ném ra ngoại lệ ArithmeticException
            System.out.println("Kết quả: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            System.out.println("Khối finally luôn được thực thi.");
        }
        System.out.println("Chương trình kết thúc.");
    }
}

