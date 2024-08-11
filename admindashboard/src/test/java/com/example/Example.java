package com.example;

class Example {
    static int staticVar = 1;  // Được khởi tạo đầu tiên

    // Khối static initializer
    static {
        System.out.println("Static initializer block");
    }

    int instanceVar = 2;       // Được khởi tạo sau static

    // Khối instance initializer
    {
        System.out.println("Instance initializer block");
    }

    // Constructor
    public Example() {
        System.out.println("Constructor");
    }

    public static void main(String[] args) {
        Example obj = new Example();
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] nums = new int[10][10];
        nums[1][1] = 1;
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i][i] + " ");
        }
        // Đường chéo chính là: 1, 5, 9
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i][i] + " "); // In ra 1 5 9
        }
        // Đường chéo phụ là: 3, 5, 7
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i][array.length - 1 - i] + " "); // In ra 3 5 7
        }
    }
}

