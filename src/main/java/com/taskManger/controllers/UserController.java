package com.taskManger.controllers;

import com.taskManger.entities.User;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.services.UserService;

public class UserController {

    UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    public User singIn(String username, String password){

        User user = userService.signIn(username, password);

        return user;
    }

    public User singUp(String username, String password, String firstName, String lastName, String phone) throws UUIDIsNotUniqueException, UsernameNotUniqueException {

        User user = userService.registerNewUser(username, password, firstName, lastName, phone);

        return user;
    }
}
