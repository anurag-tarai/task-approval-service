package com.vivatechrnd.task.service.impl;

import com.vivatechrnd.task.dto.request.TaskRequest;
import com.vivatechrnd.task.dto.response.TaskResponse;
import com.vivatechrnd.task.entity.Task;
import com.vivatechrnd.task.entity.User;
import com.vivatechrnd.task.enums.Role;
import com.vivatechrnd.task.enums.TaskStatus;
import com.vivatechrnd.task.exception.BusinessException;
import com.vivatechrnd.task.exception.ResourceNotFoundException;
import com.vivatechrnd.task.repository.TaskRepository;
import com.vivatechrnd.task.repository.UserRepository;
import com.vivatechrnd.task.security.CustomUserDetails;
import com.vivatechrnd.task.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public TaskResponse createTask(TaskRequest request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedBy(user);
        task.setStatus(TaskStatus.CREATED);

        Task savedTask = taskRepository.save(task);

        savedTask.setTaskNumber("TASK-" + String.format("%04d", savedTask.getId()));

        taskRepository.save(savedTask);

        return map(savedTask);
    }

    @Override
    public List<TaskResponse> getTasks(Authentication authentication) {
        System.out.println(authentication.getPrincipal().getClass());
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Task> tasks = user.getRole() == Role.ADMIN
                ? taskRepository.findAll()
                : taskRepository.findByCreatedBy(user);

        return tasks.stream()
                .map(this::map)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void approveTask(Long id) {
        updateStatus(id, TaskStatus.APPROVED);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void rejectTask(Long id) {
        updateStatus(id, TaskStatus.REJECTED);
    }

    private void updateStatus(Long id, TaskStatus status) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getStatus() != TaskStatus.CREATED)
            throw new BusinessException("Task already finalized");

        task.setStatus(status);
        taskRepository.save(task);
    }

    private TaskResponse map(Task task) {
        return new TaskResponse(
                task.getTaskNumber(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedBy().getUsername()
        );
    }
}