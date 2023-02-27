package com.example.backend.persistence;

import com.example.backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByUsername(String name);
  Boolean existsByUsername(String username);
  UserEntity findByUsernameAndPassword(String username, String password);
}
