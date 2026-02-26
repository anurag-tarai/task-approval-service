package com.vivatechrnd.task.dto.response;

import com.vivatechrnd.task.enums.TaskStatus;
import lombok.Data;
import lombok.Getter;

@Data
public class TaskResponse {

    private String taskNumber;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;

    public TaskResponse(String taskNumber,
                        String title,
                        String description,
                        TaskStatus status,
                        String createdBy) {
        this.taskNumber = taskNumber;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
    }
}
