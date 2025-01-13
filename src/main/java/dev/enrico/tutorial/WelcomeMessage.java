package dev.enrico.tutorial;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {
    public String getWelcomeMessage() {
        return "Welcome to my first Spring Boot Application";
    }
}
