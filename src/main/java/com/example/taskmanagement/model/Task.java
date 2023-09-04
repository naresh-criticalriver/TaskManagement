package com.example.taskmanagement.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {

    String task,taskDescription,status,userName;

    long progress;
    String dueDate;
}
