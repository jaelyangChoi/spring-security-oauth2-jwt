package org.example.springsecurityoauth2jwt.repository;

import org.example.springsecurityoauth2jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
