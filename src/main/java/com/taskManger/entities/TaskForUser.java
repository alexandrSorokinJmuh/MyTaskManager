package com.taskManger.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TaskForUser extends Entity{
    @NonNull
    String uuid;

    @NonNull
    String listUuid;

    @NonNull
    String userUuid;

    @NonNull
    String taskUuid;

    @NonNull
    String name;

}
