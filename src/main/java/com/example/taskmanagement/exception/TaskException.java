package com.example.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskException extends RuntimeException {

	public TaskException(String exception) {
		super(exception);
	}

}
