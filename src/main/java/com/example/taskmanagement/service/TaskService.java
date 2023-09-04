package com.example.taskmanagement.service;

import com.example.taskmanagement.enitity.TaskEntity;
import com.example.taskmanagement.enitity.UserEntity;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.TaskStatistics;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.request.TaskRequest;
import com.example.taskmanagement.response.Response;
import com.example.taskmanagement.utils.Constants;
import com.example.taskmanagement.utils.DateAndTimeUtil;
import com.example.taskmanagement.utils.TaskStatus;
import com.example.taskmanagement.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create new task
     * @param taskRequest
     * @return
     */
    public ResponseEntity<Response> createTask(TaskRequest taskRequest) {

        Response response = new Response();
        try {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setTask(taskRequest.getTask());
            taskEntity.setTaskDescription(taskRequest.getTaskDescription());
            taskEntity.setDueDate(DateAndTimeUtil.getLocalDate(taskRequest.getDueDate()));
            taskEntity.setCreatedTime(LocalDateTime.now());
            taskEntity.setStatus(TaskStatus.TODO.name());
            taskEntity.setProgress(0);
            taskEntity.setPriority(taskRequest.getPriority());
            taskRepository.save(taskEntity);
            response.setStatus(true);
            response.setMessage(Constants.TASK_CREATED_SUCCESSFULLY);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setStatus(false);
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Update Task based on taskId
     * @param taskRequest
     * @param taskId
     * @return
     */
    public ResponseEntity<Response> updateTask(TaskRequest taskRequest, long taskId) {
        Response response = new Response();
        response.setStatus(false);

        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            response.setMessage(Constants.NO_TASK_FOUND_WITH_ID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        try {
            TaskEntity taskEntity = taskOptional.get();
            taskEntity.setUpdatedTime(LocalDateTime.now());

            if (Validation.checkStringValue(taskRequest.getTask()))
                taskEntity.setTask(taskRequest.getTask());

            if (Validation.checkStringValue(taskRequest.getTaskDescription()))
                taskEntity.setTaskDescription(taskRequest.getTaskDescription());

            if (Validation.checkStringValue(taskRequest.getStatus()))
                taskEntity.setStatus(taskRequest.getStatus());

            if (Validation.checkStringValue(taskRequest.getDueDate()))
                taskEntity.setDueDate(DateAndTimeUtil.getLocalDate(taskRequest.getDueDate()));

            if (Validation.checkStringValue(taskRequest.getPriority()))
                taskEntity.setPriority(taskRequest.getPriority());

            if(Validation.checkProgress(taskRequest.getProgress())){
                taskEntity.setProgress(taskRequest.getProgress());
            }else {
                response.setMessage(Constants.INVALID_PROGRESS_VALUE);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }
            //If Status is Completed,update currentDate
            if (Validation.checkStringValue(taskRequest.getStatus()) && taskRequest.getStatus().equals(TaskStatus.COMPLETED.name()))
                taskEntity.setDueDate(LocalDateTime.now().toLocalDate());

            taskRepository.save(taskEntity);
            response.setStatus(true);
            response.setMessage(Constants.TASK_UPDATED_SUCCESSFULLY);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);

    }

    /**
     * List of Tasks
     * @return
     */
    public ResponseEntity<Response> getTasks() {
        Response response = new Response();
        response.setStatus(false);
        try {
            List<TaskEntity> taskEntities = taskRepository.findAll();
            List<Task> tasks = getTaskList(taskEntities);
            response.setStatus(true);
            response.setMessage(Constants.TASK_FETCHED_SUCCESSFULLY);
            response.setData(tasks);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    private List<Task> getTaskList(List<TaskEntity> taskEntities) {
        return taskEntities.stream()
                .map(taskEntity -> {
                    Task task = new Task();
                    task.setTask(taskEntity.getTask());
                    task.setTaskDescription(taskEntity.getTaskDescription());
                    task.setProgress(taskEntity.getProgress());
                    task.setStatus(taskEntity.getStatus());
                    task.setDueDate(taskEntity.getDueDate().toString());
                    Optional<UserEntity> user = userRepository.findById(taskEntity.getUserID());
                    task.setUserName(user.map(UserEntity::getName).orElse(""));

                    return task;
                })
                .collect(Collectors.toList());

    }

    /**
     * Get List of Tasks based on task status
     * @param taskStatus
     * @return
     */
    public ResponseEntity<Response> getTasksByStatus(String taskStatus) {
        Response response = new Response();
        response.setStatus(false);
        try {
            List<TaskEntity> taskEntities = taskRepository.findAll()
                    .stream()
                    .filter(task -> task.getStatus().equals(taskStatus))
                    .collect(Collectors.toList());

            List<Task> taskList = getTaskList(taskEntities);

            response.setStatus(true);
            response.setMessage(Constants.TASK_FETCHED_SUCCESSFULLY);
            response.setData(taskList);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Delete Task
     * @param taskId
     * @return
     */
    public ResponseEntity<Response> deleteTask(long taskId) {
        Response response = new Response();
        response.setStatus(false);
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            response.setMessage(Constants.NO_TASK_FOUND_WITH_ID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
        taskRepository.deleteById(taskId);
        response.setStatus(true);
        response.setMessage(Constants.TASK_DELETE_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Assign task to User
     * @param taskId
     * @param userID
     * @return
     */
    public ResponseEntity<Response> assignTaskByUserId(long taskId, long userID) {
        Response response = new Response();
        response.setStatus(false);

        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            response.setMessage(Constants.NO_TASK_FOUND_WITH_ID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        Optional<UserEntity> userOptional = userRepository.findById(userID);
        if (!userOptional.isPresent()) {
            response.setMessage(Constants.NO_USER_FOUND_WITH_ID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        try {
            TaskEntity taskEntity = taskOptional.get();
            taskEntity.setUserID(userID);
            taskRepository.save(taskEntity);
            response.setStatus(true);
            response.setMessage(Constants.TASK_ASSIGNED_SUCCESSFULLY);
        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Response> getTasksByUserId(long userID) {
        Response response = new Response();
        response.setStatus(false);

        Optional<UserEntity> userOptional = userRepository.findById(userID);
        if (!userOptional.isPresent()) {
            response.setMessage(Constants.NO_USER_FOUND_WITH_ID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        try {
            List<TaskEntity> tasks = taskRepository.findByUserID(userID)
                    .stream()
                   // .filter(task -> task.getUserID() == userID)
                    .collect(Collectors.toList());
            response.setStatus(true);
            response.setMessage(Constants.TASK_FETCHED_SUCCESSFULLY);
            response.setData(tasks);
        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Updated task Progress
     * @param taskId
     * @param progressValue
     * @return
     */
    public ResponseEntity<Response> updateTaskProgress(long taskId, long progressValue) {
        Response response = new Response();
        response.setStatus(false);

        try {
            Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
            if (!taskOptional.isPresent()) {
                response.setMessage(Constants.NO_TASK_FOUND_WITH_ID);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);
            }
            if(!Validation.checkProgress(progressValue)){
                response.setMessage(Constants.INVALID_PROGRESS_VALUE);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }
            TaskEntity taskEntity = taskOptional.get();
            taskEntity.setProgress(progressValue);
            taskRepository.save(taskEntity);
            response.setStatus(true);
            response.setMessage(Constants.TASK_PROGRESS_UPDATED_SUCCESSFULLY);
        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /**
     * OverDue Task based on Current Date
     * @return
     */
    public ResponseEntity<Response> getOverdueTasks() {
        Response response = new Response();
        response.setStatus(false);
        try {
            LocalDate currentDate = LocalDate.now(); //2023-09-04
            logger.info("Date "+currentDate);
            List<TaskEntity> taskEntities= taskRepository.findAll()
                    .stream()
                    .filter(task -> task.getDueDate().isAfter(currentDate))
                    .collect(Collectors.toList());

            List<Task> taskList = getTaskList(taskEntities);

            response.setStatus(true);
            response.setMessage(Constants.OVERDUE_TASK_FETCHED_SUCCESSFULLY);
            response.setData(taskList);

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Response> getCompletedTasks(LocalDate startDate, LocalDate endDate) {
        Response response = new Response();
        response.setStatus(false);
        try {
            List<TaskEntity> taskEntities = taskRepository.findByDueDateBetween(startDate,endDate)
                    .stream()
                    .filter(task -> task.getStatus().equals(TaskStatus.COMPLETED.name()))
                    .collect(Collectors.toList());

            List<Task> taskList = getTaskList(taskEntities);

            response.setStatus(true);
            response.setMessage(Constants.COMPLETED_TASK_FETCHED_SUCCESSFULLY);
            response.setData(taskList);

        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<Response> getTaskStatistics() {
        Response response = new Response();
        response.setStatus(false);
        try {
            List<TaskEntity> allTasks = taskRepository.findAll();
            List<TaskEntity> completedTasks = taskRepository.findByStatus(TaskStatus.COMPLETED.name());
//            List<TaskEntity> completedTasks = allTasks.stream()
//                                                .filter(task -> task.getStatus().equals(TaskStatus.COMPLETED))
//                                                .collect(Collectors.toList());
            double percentageCompleted = (completedTasks.size() / (double) allTasks.size()) * 100;

            TaskStatistics taskStatistics = new TaskStatistics();
            taskStatistics.setTotalTasks(allTasks.size());
            taskStatistics.setCompletedTasks(completedTasks.size());
            taskStatistics.setTaskPercentage(percentageCompleted);

            response.setStatus(true);
            response.setMessage(Constants.COMPLETED_TASK_FETCHED_SUCCESSFULLY);
            response.setData(taskStatistics);

        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
