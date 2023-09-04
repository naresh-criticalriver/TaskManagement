package com.example.taskmanagement.controller;

import com.example.taskmanagement.request.UserRequest;
import com.example.taskmanagement.response.Response;
import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "User created Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Get a list of tasks assigned to a specific user"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/{userId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getTasksByUserId(@PathVariable(value = "userId") long userID) {
        return taskService.getTasksByUserId(userID);
    }
}
