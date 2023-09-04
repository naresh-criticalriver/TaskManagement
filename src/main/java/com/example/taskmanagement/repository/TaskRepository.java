package com.example.taskmanagement.repository;

import com.example.taskmanagement.enitity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findByUserID(long userId);
    List<TaskEntity> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    List<TaskEntity> findByStatus(String status);

    List<TaskEntity> findByDueDateBefore(LocalDate currentDate);



}
