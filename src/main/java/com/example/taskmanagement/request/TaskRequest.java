package com.example.taskmanagement.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    String task,taskDescription,status,dueDate,priority;
    long progress;

}
