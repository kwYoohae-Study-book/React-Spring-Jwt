package com.example.backend.dto;

import com.example.backend.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

  private String id;
  private String title;
  private boolean done;

  public TodoDTO(final TodoEntity entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.done = entity.isDone();
  }

  public static TodoEntity toEntity(final TodoDTO dto) {
    return new TodoEntity(dto.getId(), null, dto.getTitle(), dto.isDone());
  }

}
