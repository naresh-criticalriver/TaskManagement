package com.example.taskmanagement.enitity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
public class TaskEntity extends  BaseEntity{

    @Column(name = "task")
    String task;
    @Column(name = "task_description")
    String taskDescription;
    @Column(name = "status")
    String status;
    @Column(name = "due_date")
    LocalDate dueDate;
    @Column(name = "assign_id")
    long userID;
    @Column(name = "progress")
    long progress;

    @Column(name = "priority")
    String priority;


}
