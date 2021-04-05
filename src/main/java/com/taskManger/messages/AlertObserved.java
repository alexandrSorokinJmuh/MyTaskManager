package com.taskManger.messages;

import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class AlertObserved implements Observed{
    Tasks task;
    @Getter
    List<Observer> userList = new LinkedList<>();

    public AlertObserved(Tasks task) {
        this.task = task;
    }

    @Override
    public void addObserver(Observer observer) {
        userList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        userList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : userList){
            notifyObserver(observer);
        }
    }

    @Override
    public void notifyObserver(Observer observer) {
        observer.handleEvent(task);
    }
}
