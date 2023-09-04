package com.example.taskmanagement.controller;

import com.example.taskmanagement.request.TaskRequest;
import com.example.taskmanagement.response.Response;
import com.example.taskmanagement.service.TaskService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task created Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @PostMapping(value = "/createTask", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createTask(@RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task updated Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @PutMapping(value = "/updateTask/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateTask(@PathVariable(value = "taskId") long taskId,@RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(taskRequest,taskId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task Deleted Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/deleteTask/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteTask(@PathVariable(value = "taskId") long taskId) {
        return taskService.deleteTask(taskId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task fetched Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getTasks() {
        return taskService.getTasks();
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task fetched Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getTasksByStatus(@PathVariable(value = "status") String status) {
        return taskService.getTasksByStatus(status);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task Assigned Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @PutMapping(value = "/{taskId}/assign/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> assignTask(@PathVariable(value = "taskId") long taskId, @PathVariable(value = "userId") long userID) {
        return taskService.assignTaskByUserId(taskId,userID);
    }

    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task progress updated Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @PutMapping(value = "/{taskId}/progress/{progress}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateTaskProgress(@PathVariable(value = "taskId") long taskId, @PathVariable(value = "progress") long progressValue) {
        return taskService.updateTaskProgress(taskId,progressValue);
    }

    /**
     * List of OverDue Tasks
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Overdue Task fetched Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/overdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    /**
     * List of Completed Tasks
     * @param startDate
     * @param endDate
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = " Completed Task fetched Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/completed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getCompletedTasks(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return taskService.getCompletedTasks(startDate,endDate);
    }

    /**
     * Task statistics
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, response = Response.class, message = "Task statistics fetched Successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, response = String.class, message = "Invalid parameters"),
            @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, response = String.class, message = "Invalid Token / Without Token"),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, response = String.class, message = "UnAuthorized Access")})
    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getTasksStatistics() {
        return taskService.getTaskStatistics();
    }

}
