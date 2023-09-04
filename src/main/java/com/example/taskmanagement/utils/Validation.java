package com.example.taskmanagement.utils;

public class Validation {
    public static boolean checkStringValue(String value) {
        return value != null && value.length() > 0;
    }

    public static boolean checkProgress(long progressValue) {
        return progressValue>=0 && progressValue<=100;
    }
}
