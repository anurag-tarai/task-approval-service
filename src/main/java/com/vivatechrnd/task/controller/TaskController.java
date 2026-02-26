package com.vivatechrnd.task.controller;

import com.vivatechrnd.task.dto.request.TaskRequest;
import com.vivatechrnd.task.dto.response.TaskResponse;
import com.vivatechrnd.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {

        TaskResponse response =
                taskService.createTask(request, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            Authentication authentication) {

        return ResponseEntity.ok(
                taskService.getTasks(authentication)
        );
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        taskService.approveTask(id);
        return ResponseEntity.ok("Task approved");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        taskService.rejectTask(id);
        return ResponseEntity.ok("Task rejected");
    }
}
