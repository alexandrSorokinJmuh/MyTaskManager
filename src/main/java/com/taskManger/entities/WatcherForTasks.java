package com.taskManger.entities;

import lombok.Data;
import lombok.NonNull;


@Data
public class WatcherForTasks implements Entity {
    @NonNull
    String contactUuid;
    @NonNull
    String userUuid;
}
