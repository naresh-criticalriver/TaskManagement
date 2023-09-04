package com.example.taskmanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Response {
    private Boolean status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

}
