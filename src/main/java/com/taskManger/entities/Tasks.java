package com.taskManger.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Tasks extends Entity {

    @NonNull
    String uuid;
    @NonNull
    String name;

    @NonNull
    String creatorUuid;

    String description = "";
//    @JsonSerialize(using= DateSerializer.class)
    Date alert_time = new Date();
    Boolean alert_received = false;

    public Tasks(@NonNull String uuid, @NonNull String name, @NonNull String creatorUuid, String description, Date alert_time, Boolean alert_received) {
        this.uuid = uuid;
        this.name = name;
        this.creatorUuid = creatorUuid;
        this.description = description;
        this.alert_time = alert_time;
        this.alert_received = alert_received;
    }
}
