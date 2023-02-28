package com.example.backend.controller;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.TodoDTO;
import com.example.backend.model.TodoEntity;
import com.example.backend.service.TodoService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @PostMapping
  public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
    try {
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity = new TodoEntity(null, userId, entity.getTitle(), entity.isDone());

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
  public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
    final List<TodoEntity> entities = todoService.retrieve(userId);
    final List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    final ResponseDTO<TodoDTO> response = new ResponseDTO<>(null, dtos);

    return ResponseEntity.ok().body(response);
  }

  @PutMapping
  public ResponseEntity<?> updateTodo( @AuthenticationPrincipal String userId,
      @RequestBody TodoDTO dto) {
    TodoEntity entity = TodoDTO.toEntity(dto);
    entity = new TodoEntity(entity.getId(), userId, entity.getTitle(), entity.isDone());

    final List<TodoEntity> entities = todoService.update(entity);
    final List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    final ResponseDTO<TodoDTO> response = new ResponseDTO<>(null, dtos);

    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo( @AuthenticationPrincipal String userId,
      @RequestBody TodoDTO dto) {
    try {
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity = new TodoEntity(entity.getId(), userId, entity.getTitle(), entity.isDone());

      final List<TodoEntity> entities = todoService.delete(entity);
      final List<TodoDTO> dtos = entities.stream().map(TodoDTO::new)
          .collect(Collectors.toList());
      final ResponseDTO<TodoDTO> response = new ResponseDTO<>(null, dtos);

      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      final ResponseDTO<TodoDTO> response = new ResponseDTO<>(e.getMessage(), null);
      return ResponseEntity.badRequest().body(response);
    }
  }

}
