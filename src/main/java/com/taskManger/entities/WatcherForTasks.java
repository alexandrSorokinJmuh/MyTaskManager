package com.taskManger.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NonNull;


@Data
public class WatcherForTasks extends Entity {
    @NonNull
    String contactUuid;
    @NonNull
    String userUuid;

    public WatcherForTasks(@NonNull String contactUuid, @NonNull String userUuid) {
        this.contactUuid = contactUuid;
        this.userUuid = userUuid;
    }
}
