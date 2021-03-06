package com.taskManger.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ListOfTasks extends Entity {
    @NonNull
    String uuid;
    @NonNull
    String creatorUuid;

    @NonNull
    String name;
}
