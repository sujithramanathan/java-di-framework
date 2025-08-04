package com.di.app.services;

import com.java.di.annotation.Component;
import com.java.di.annotation.Inject;

@Component
public class UserService {

    @Inject
    private EmailService emailService;

    public void registerUser(String username) {
        System.out.println("User registered: " + username);
        emailService.sendEmail(username, "Welcome!");
    }
}
