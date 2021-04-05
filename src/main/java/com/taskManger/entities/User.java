package com.taskManger.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taskManger.messages.Observer;
import lombok.*;
import org.joda.time.DateTime;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class User extends Entity implements Observer {

    public User(@NonNull String uuid, @NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, String phone) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public User(){

    }
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
    String phone = "";

    @Override
    public void handleEvent(Tasks taskToDo) {
        if (taskToDo.getAlert_time().before(DateTime.now().toDate())){
            System.out.println(String.format("Task %s time is out (Alert time: %s)", taskToDo.getName(), taskToDo.getAlert_time()));
        }
    }
}
