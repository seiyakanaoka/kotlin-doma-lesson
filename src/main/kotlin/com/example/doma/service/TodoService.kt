package com.example.doma.service

import com.example.doma.dao.TodoDao
import com.example.doma.entity.Todo
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TodoService(private val todoDao: TodoDao) {
  fun findAll(name: String?): List<Todo?>? {
    return todoDao.findAll(name)
  }
}