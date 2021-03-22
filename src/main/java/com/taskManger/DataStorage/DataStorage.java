package com.taskManger.DataStorage;

import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.entities.WatcherForTasks;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    List<User> userList;
    List<Tasks> tasksList;
    List<ListOfTasks> listOfTasks;
    List<WatcherForTasks> watcherForTasksList;

    private DataStorage(){

    }

    public DataStorage getInstance(){
        return this;
    }

    public List<User> getUserList() {
        return new ArrayList(userList);
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
