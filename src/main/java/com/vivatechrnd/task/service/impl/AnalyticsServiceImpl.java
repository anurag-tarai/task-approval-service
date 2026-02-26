package com.vivatechrnd.task.service.impl;

import com.vivatechrnd.task.repository.TaskRepository;
import com.vivatechrnd.task.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TaskRepository taskRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getTasksByStatus() {

        return taskRepository.countTasksByStatus()
                .stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(),
                        obj -> (Long) obj[1]
                ));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getDailyTaskCount() {

        return taskRepository.countTasksByDate()
                .stream()
                .collect(Collectors.toMap(
                        obj -> obj[0].toString(),
                        obj -> ((Number) obj[1]).longValue()
                ));
    }
}
