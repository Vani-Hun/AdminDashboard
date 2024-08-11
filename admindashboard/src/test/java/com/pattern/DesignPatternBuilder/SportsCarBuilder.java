package com.pattern.DesignPatternBuilder;

public class SportsCarBuilder implements CarBuilder {
    private final Car car;

    public SportsCarBuilder() {
        this.car = new Car();
    }

    @Override
    public void buildEngine() {
        car.setEngine("V8 Engine");
    }

    @Override
    public void buildBody() {
        car.setBody("Sports Body");
    }

    @Override
    public void buildSeats() {
        car.setSeats(2);
    }

    @Override
    public Car getCar() {
        return this.car;
    }
}
