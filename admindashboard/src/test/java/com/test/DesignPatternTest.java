package com.test;

import com.pattern.DesignPatternBuilder.Car;
import com.pattern.DesignPatternBuilder.CarBuilder;
import com.pattern.DesignPatternBuilder.CarDirector;
import com.pattern.DesignPatternBuilder.SportsCarBuilder;
import com.pattern.DesignPatternSingleton.Singleton;
import org.junit.jupiter.api.Test;


public class DesignPatternTest {
    @Test
    void testBuilder() {
        CarBuilder builder = new SportsCarBuilder();
        CarDirector director = new CarDirector(builder);
        director.buildCar();
        Car car = director.getCar();
        System.out.println(car);
    }

    @Test
    void testSingleton() {
        Singleton singleton = Singleton.getInstance();
        singleton.showMessage();
    }
}
