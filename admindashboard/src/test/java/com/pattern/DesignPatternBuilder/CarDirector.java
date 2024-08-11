package com.pattern.DesignPatternBuilder;

public class CarDirector {
    private final CarBuilder builder;

    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }

    public void buildCar() {
        builder.buildEngine();
        builder.buildBody();
        builder.buildSeats();
    }

    public Car getCar() {
        return builder.getCar();
    }
}
