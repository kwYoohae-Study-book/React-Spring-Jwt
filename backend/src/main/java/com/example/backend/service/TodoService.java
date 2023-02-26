package com.example.backend.service;

import com.example.backend.model.TodoEntity;
import com.example.backend.persistence.TodoRepository;
import com.example.backend.utils.ErrorMessage;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  public List<TodoEntity> create(final TodoEntity entity) {
    validate(entity);

    todoRepository.save(entity);
    log.info("Entity Id : {} 가 저장되었습니다", entity.getId());

    return todoRepository.findByUserId(entity.getUserId());
  }

  public List<TodoEntity> retrieve(final String userId) {
    return todoRepository.findByUserId(userId);
  }

  public List<TodoEntity> update(final TodoEntity entity) {
    validate(entity);

    final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

    original.ifPresent(todo -> {
      final TodoEntity newEntity = new TodoEntity(todo.getId(), todo.getUserId(), entity.getTitle(),
          entity.isDone());
      todoRepository.save(newEntity);
    });

    return retrieve(entity.getUserId());
  }

  private static void validate(final TodoEntity entity) {
    if (entity == null) {
      log.warn(ErrorMessage.ENTITY_IS_NOT_NULL);
      throw new RuntimeException(ErrorMessage.ENTITY_IS_NOT_NULL);
    }

    if (entity.getUserId() == null) {
      log.warn(ErrorMessage.UNKNOWN_USER);
      throw new RuntimeException(ErrorMessage.UNKNOWN_USER);
    }
  }

}
