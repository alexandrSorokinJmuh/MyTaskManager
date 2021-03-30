package com.taskManger.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NonNull;

@Data
public class ListOfTasks extends Entity {
    @NonNull
    String uuid;
    @NonNull
    String creatorUuid;
    @NonNull
    String userUuid;
    @NonNull
    String taskUuid;

    public ListOfTasks(@NonNull String uuid, @NonNull String creatorUuid, @NonNull String userUuid, @NonNull String taskUuid, @NonNull String name) {
        this.uuid = uuid;
        this.creatorUuid = creatorUuid;
        this.userUuid = userUuid;
        this.taskUuid = taskUuid;
        this.name = name;
    }

    @NonNull
    String name;
}
