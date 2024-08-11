package com.pattern.DesignPatternBuilder;

public class Car {
    private String engine;
    private String body;
    private int seats;

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Car [engine=" + engine + ", body=" + body + ", seats=" + seats + "]";
    }
}
