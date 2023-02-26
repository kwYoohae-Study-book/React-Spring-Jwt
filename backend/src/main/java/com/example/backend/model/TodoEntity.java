package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "Todo")
public class TodoEntity {

  @Id
  @GeneratedValue(generator = "system-uuid") // 기본 generator
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  private String userId;
  private String title;
  private boolean done;

  public void makeTemporalUser() {
    this.userId = "temporary-user";
  }

  public void clearId() {
    this.id = null;
  }
}
