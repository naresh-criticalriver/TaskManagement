package com.example.taskmanagement.repository;

import com.example.taskmanagement.enitity.TaskEntity;
import com.example.taskmanagement.enitity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findById(long id);

//    Optional<String> findByName(long id);
}
