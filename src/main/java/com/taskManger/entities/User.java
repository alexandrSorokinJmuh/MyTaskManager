package com.taskManger.entities;

import lombok.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class User implements Entity {

    @NonNull
    private String uuid;

//    private User(){
//    }
//
//    public User(String username, String password){
//        this();
//        this.username = username;
//        this.password = password;
//    }
//
//    public User(String username, String password, String firstName, String lastName){
//        this(username, password);
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//    public User(String username, String password, String firstName, String lastName, String phone) {
//        this(username, password, firstName, lastName);
//        this.phone = phone;
//    }

    @NonNull
    String username;
    @NonNull
    String password;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    String phone;
}
