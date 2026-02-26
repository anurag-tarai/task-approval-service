package com.vivatechrnd.task.service;

import com.vivatechrnd.task.dto.request.TaskRequest;
import com.vivatechrnd.task.dto.response.TaskResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request, String username);
    List<TaskResponse> getTasks(Authentication authentication);
    void approveTask(Long id);
    void rejectTask(Long id);
}
