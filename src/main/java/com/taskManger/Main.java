package com.taskManger;

import com.taskManger.entities.User;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        List<User> userList = new LinkedList<User>();
        for(int i = 0; i < 10; i++){
            userList.add(new User(UUID.randomUUID().toString(), "asd" + i, "123", "j" + i, "l" + i));
        }

        System.out.println(userList);
    }
}
