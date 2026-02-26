package com.vivatechrnd.task.controller;

import com.vivatechrnd.task.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints for task analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(summary = "Get task counts by status", description = "Returns a map of task status (CREATED, APPROVED, REJECTED) to their respective counts")
    @GetMapping("/tasks-by-status")
    public ResponseEntity<Map<String, Long>> tasksByStatus() {
        return ResponseEntity.ok(
                analyticsService.getTasksByStatus()
        );
    }

    @Operation(summary = "Get daily task count", description = "Returns a map of date to number of tasks created on that day")
    @GetMapping("/daily-task-count")
    public ResponseEntity<Map<String, Long>> dailyTaskCount() {
        return ResponseEntity.ok(
                analyticsService.getDailyTaskCount()
        );
    }
}