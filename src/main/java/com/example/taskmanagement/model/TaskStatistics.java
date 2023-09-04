package com.example.taskmanagement.model;

import lombok.Data;

@Data
public class TaskStatistics {

    long totalTasks,completedTasks;
    double taskPercentage;
}
