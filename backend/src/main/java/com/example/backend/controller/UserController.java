package com.example.backend.controller;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.UserEntity;
import com.example.backend.security.TokenProvider;
import com.example.backend.service.UserService;
import com.example.backend.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
      if (userDTO == null || userDTO.getPassword() == null) {
        throw new RuntimeException(ErrorMessage.INVALID_PASSWORD);
      }

      final UserEntity user = UserEntity.builder()
          .username(userDTO.getUsername())
          .password(userDTO.getPassword())
          .build();

      final UserEntity registeredUser = userService.create(user);
      final UserDTO responseUserDTO = UserDTO.builder()
          .id(registeredUser.getId())
          .username(registeredUser.getUsername())
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      final ResponseDTO<Object> responseDTO = new ResponseDTO<>(e.getMessage(), null);
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
    final UserEntity user = userService.getByCredentials(userDTO.getUsername(),
        userDTO.getPassword());

    if (user != null) {
      final String token = tokenProvider.create(user);
      final UserDTO responseUserDTO = UserDTO.builder()
          .username(user.getUsername())
          .id(user.getId())
          .token(token)
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    }

    final ResponseDTO<Object> responseDTO = new ResponseDTO<>(ErrorMessage.LOGIN_FAILED, null);
    return ResponseEntity.badRequest().body(responseDTO);
  }
}
