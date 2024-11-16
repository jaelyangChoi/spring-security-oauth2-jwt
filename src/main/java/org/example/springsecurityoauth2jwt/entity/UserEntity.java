package org.example.springsecurityoauth2jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String name;
    private String email;
    private String role;
}
