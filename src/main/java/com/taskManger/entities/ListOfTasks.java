package com.taskManger.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public class ListOfTasks implements Entity {
    @NonNull
    String uuid;
    @NonNull
    String userUuid;
    @NonNull
    String taskUuid;
    @NonNull
    String name;
}
