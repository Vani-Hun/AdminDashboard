package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
//        Logger logger = Logger.getLogger(HttpTransport.class.getName());
//        ConsoleHandler consoleHandler = new ConsoleHandler();
//        consoleHandler.setLevel(Level.ALL);
//        logger.addHandler(consoleHandler);
//        logger.setLevel(Level.ALL);

    }
}
