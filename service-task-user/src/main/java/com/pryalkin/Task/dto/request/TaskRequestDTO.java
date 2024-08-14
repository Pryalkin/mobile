package com.pryalkin.Task.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class TaskRequestDTO {

    private String heading;
    private String description;
    private String priority;

}
