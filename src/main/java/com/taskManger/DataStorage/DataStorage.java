package com.taskManger.DataStorage;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.entities.WatcherForTasks;
import lombok.ToString;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ToString
public class DataStorage {
    private static DataStorage instance;
    List<User> userList = new ArrayList<>();
    List<Tasks> tasksList = new ArrayList<>();
    List<ListOfTasks> listOfTasks = new ArrayList<>();
    List<WatcherForTasks> watcherForTasksList = new ArrayList<>();


    private DataStorage() {

    }



    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Tasks> getTasksList() {
        return new ArrayList(tasksList);
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }

    public List<ListOfTasks> getListOfTasks() {
        return new ArrayList(listOfTasks);
    }

    public void setListOfTasks(List<ListOfTasks> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    public List<WatcherForTasks> getWatcherForTasksList() {
        return new ArrayList(watcherForTasksList);
    }

    public void setWatcherForTasksList(List<WatcherForTasks> watcherForTasksList) {
        this.watcherForTasksList = watcherForTasksList;
    }


}
