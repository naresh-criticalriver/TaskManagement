package com.example.taskmanagement.service;

import com.example.taskmanagement.enitity.TaskEntity;
import com.example.taskmanagement.enitity.UserEntity;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.request.TaskRequest;
import com.example.taskmanagement.request.UserRequest;
import com.example.taskmanagement.response.Response;
import com.example.taskmanagement.utils.Constants;
import com.example.taskmanagement.utils.DateAndTimeUtil;
import com.example.taskmanagement.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    /**
     *
     * @param userRequest
     * @return
     */
    public ResponseEntity<Response> createUser(UserRequest userRequest) {

        Response response = new Response();
        response.setStatus(false);
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userRequest.getName());
            userEntity.setDesignation(userRequest.getDesignation());
            userEntity.setCreatedTime(LocalDateTime.now());
            userRepository.save(userEntity);
            response.setStatus(true);
            response.setMessage(Constants.USER_CREATED_SUCCESSFULLY);

        } catch (Exception e) {
            response.setMessage(Constants.SOME_THING_WENT_WRONG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
