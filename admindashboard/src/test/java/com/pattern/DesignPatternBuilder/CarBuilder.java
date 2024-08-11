package com.pattern.DesignPatternBuilder;

public interface CarBuilder {
    void buildEngine();

    void buildBody();

    void buildSeats();

    Car getCar();
}
