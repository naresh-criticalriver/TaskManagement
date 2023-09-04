package com.example.taskmanagement.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Constants {

    private Constants() {

    }

    public static final char DOT = '.';
    public static final char SLASH = '/';
    public static final String SUCCESS = "SUCCESS";
    public static final String CREATED = "CREATED";
    public static final String FAILED = "FAILED";
    public static final String NO_TASK_FOUND_WITH_ID = "No task found with id :";
    public static final String NO_USER_FOUND_WITH_ID = "No user found with id :";
    public static final String SOME_THING_WENT_WRONG = "Some thing went wrong";
    public static final String TASK_CREATED_SUCCESSFULLY = "Task Created Successfully";
    public static final String TASK_UPDATED_SUCCESSFULLY = "Task Updated Successfully";
    public static final String TASK_FETCHED_SUCCESSFULLY = "Task Fetched Successfully";
    public static final String TASK_DELETE_SUCCESSFULLY = "Task Deleted Successfully";
    public static final String USER_CREATED_SUCCESSFULLY = "User Created Successfully";
    public static final String TASK_ASSIGNED_SUCCESSFULLY = "Task Assigned Successfully";
    public static final String TASK_PROGRESS_UPDATED_SUCCESSFULLY = "Task Progress Updated Successfully";
    public static final String OVERDUE_TASK_FETCHED_SUCCESSFULLY = "Overdue Task Fetched Successfully";
    public static final String COMPLETED_TASK_FETCHED_SUCCESSFULLY = "Completed Task Fetched Successfully";

    public static final String INVALID_PROGRESS_VALUE = "Invalid Progress Value";
}
