package com.example;

public class FinalizeExample {
    public static void main(String[] args) {
        FinalizeExample example = new FinalizeExample();

        // Đặt đối tượng về null để trở thành đối tượng không còn sử dụng
        example = null;

        // Yêu cầu Garbage Collector thực thi
        System.gc();

        System.out.println("Chương trình kết thúc.");
    }

    // Ghi đè phương thức finalize để minh họa
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Phương thức finalize() được gọi.");
    }
}
