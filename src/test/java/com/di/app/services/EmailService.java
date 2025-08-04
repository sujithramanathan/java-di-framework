package com.di.app.services;

import com.java.di.annotation.Component;

@Component
public class EmailService {

    public void sendEmail(String to, String message) {
        System.out.println("Email sent to "+to+": "+message);
    }

}
