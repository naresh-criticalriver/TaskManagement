package com.example.taskmanagement.service;

import com.example.taskmanagement.enitity.TaskEntity;

import java.util.Comparator;
import java.util.Objects;

public class TaskComparator implements Comparator<TaskEntity> {
    @Override
    public int compare(TaskEntity task1, TaskEntity task2) {
        if (!Objects.equals(task1.getPriority(), task2.getPriority())) {
            // Sort by priority (High > Medium > Low)
            return task1.getPriority().compareTo(task2.getPriority());
        } else {
            // Within the same priority, sort by due date
            return task1.getDueDate().compareTo(task2.getDueDate());
        }
    }
}

