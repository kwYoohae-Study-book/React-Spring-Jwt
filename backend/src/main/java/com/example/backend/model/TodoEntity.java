package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoEntity {

  @Id
  private String id;
  private String userId;
  private String title;
  private boolean done;
}
