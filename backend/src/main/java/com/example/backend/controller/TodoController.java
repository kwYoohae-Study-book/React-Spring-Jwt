package com.example.backend.controller;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.TodoDTO;
import com.example.backend.model.TodoEntity;
import com.example.backend.service.TodoService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
    try {
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity.makeTemporalUser();

      final List<TodoEntity> entities = todoService.create(entity);
      final List<TodoDTO> dtos = entities.stream().map(TodoDTO::new)
          .collect(Collectors.toList());
      final ResponseDTO<TodoDTO> response = new ResponseDTO<>(null, dtos);

      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      final ResponseDTO<TodoDTO> response = new ResponseDTO<>(e.getMessage(), null);
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList() {
    String temporaryUserId = "temporary-user";

    final List<TodoEntity> entities = todoService.retrieve(temporaryUserId);
    final List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    final ResponseDTO<TodoDTO> response = new ResponseDTO<>(null, dtos);

    return ResponseEntity.ok().body(response);
  }

}
