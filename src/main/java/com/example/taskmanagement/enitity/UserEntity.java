package com.example.taskmanagement.enitity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class UserEntity extends BaseEntity {

    @Column(name = "name")
    String name;
    @Column(name = "designation")
    String designation;

}
