package com.vivatechrnd.task.controller;

import com.vivatechrnd.task.dto.request.TaskRequest;
import com.vivatechrnd.task.dto.response.TaskResponse;
import com.vivatechrnd.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tasks", description = "Endpoints for task management and approval workflow")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "USER role only can create tasks")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {

        TaskResponse response =
                taskService.createTask(request, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get tasks", description = "USER sees own tasks, ADMIN sees all tasks")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            Authentication authentication) {

        return ResponseEntity.ok(
                taskService.getTasks(authentication)
        );
    }

    @Operation(summary = "Approve a task", description = "ADMIN role only can approve tasks")
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        taskService.approveTask(id);
        return ResponseEntity.ok("Task approved");
    }

    @Operation(summary = "Reject a task", description = "ADMIN role only can reject tasks")
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        taskService.rejectTask(id);
        return ResponseEntity.ok("Task rejected");
    }
}