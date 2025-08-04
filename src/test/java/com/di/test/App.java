package com.di.test;

import com.di.test.services.UserService;
import com.java.di.container.DIContainer;

public class App {

    public static void main(String[] args) {
        DIContainer container = new DIContainer();
        container.register("com.di.test");

        UserService userService = container.getBean(UserService.class);
        userService.registerUser("hellowWorld@gmail.com");
    }

}
