package com.example.backend.persistence;

import com.example.backend.model.TodoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//  @Query("select * from TodoEntity t where t.userId = ?1")
//  List<TodoEntity> findByUserIdQuery(String userId);

  List<TodoEntity> findByUserId(String userId);
}
