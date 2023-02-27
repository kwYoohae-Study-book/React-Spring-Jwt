package com.example.backend.service;

import com.example.backend.model.UserEntity;
import com.example.backend.persistence.UserRepository;
import com.example.backend.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if (userEntity == null || userEntity.getUsername() == null) {
      throw new RuntimeException(ErrorMessage.INVALID_ARGUMENT);
    }

    final String username = userEntity.getUsername();
    if (userRepository.existsByUsername(username)) {
      log.warn(ErrorMessage.ALREADY_EXISTS_USERNAME +" {}", username);
      throw new RuntimeException(ErrorMessage.ALREADY_EXISTS_USERNAME);
    }

    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(final String username, final String  password) {
    return userRepository.findByUsernameAndPassword(username, password);
  }
}
