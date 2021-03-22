package com.taskManger.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Tasks implements Entity {

    @NonNull
    String uuid;
    @NonNull
    String name;

    String description;

    Date alert_time;
    Boolean alert_received;
}
