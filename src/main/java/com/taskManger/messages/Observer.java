package com.taskManger.messages;

import com.taskManger.entities.Tasks;

import java.util.List;

public interface Observer {
    void handleEvent(Tasks taskToDo);
}
