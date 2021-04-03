package com.taskManger.controllers;

import com.taskManger.entities.User;
import com.taskManger.exception.EntityNotFoundException;
import com.taskManger.exception.UUIDIsNotUniqueException;
import com.taskManger.exception.UsernameNotUniqueException;
import com.taskManger.services.UserService;
import lombok.NonNull;

import java.util.List;

public class UserController {

    UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    public User singIn(@NonNull String username, @NonNull String password){

        User user = userService.signIn(username, password);

        return user;
    }

    public User singUp(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, @NonNull String phone) throws UUIDIsNotUniqueException, UsernameNotUniqueException {

        User user = userService.registerNewUser(username, password, firstName, lastName, phone);

        return user;
    }

    public User getUserByUuid(@NonNull String userUuid) throws UUIDIsNotUniqueException, EntityNotFoundException {
        User user = userService.getUserByUuid(userUuid);

        return user;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
