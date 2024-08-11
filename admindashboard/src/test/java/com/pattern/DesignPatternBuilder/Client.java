package com.pattern.DesignPatternBuilder;

public class Client {
    public static void main(String[] args) {
        CarBuilder builder = new SportsCarBuilder();
        CarDirector director = new CarDirector(builder);
        director.buildCar();
        Car car = director.getCar();
        System.out.println(car);
    }
}
