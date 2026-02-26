package com.vivatechrnd.task.repository;

import com.vivatechrnd.task.entity.Task;
import com.vivatechrnd.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCreatedBy(User user);

    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> countTasksByStatus();

    @Query(value = "SELECT DATE(created_at), COUNT(*) FROM tasks GROUP BY DATE(created_at)", nativeQuery = true)
    List<Object[]> countTasksByDate();
}
