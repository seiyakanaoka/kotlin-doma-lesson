package com.example.doma.service

import com.example.doma.dao.TodoDao
import com.example.doma.entity.Todo
import com.example.doma.form.TodoForm
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
@RequiredArgsConstructor
class TodoService(private val todoDao: TodoDao) {
  fun findAll(name: String?): List<Todo> {
    return todoDao.findAll(name)
  }

  fun findById(id: Int): Todo {
    return todoDao.findById(id)
  }

  fun post(todoForm: TodoForm): Int {
    val todo = Todo(null, todoForm.name)
    return requireNotNull(todoDao.insert(todo).entity.id) {
      "登録に失敗しました。"
    }
  }

  fun update(todoForm: TodoForm): Int {
    val currentTodo = requireNotNull(todoForm.id?.let { findById(it) })
    val updateTime = Date.from(
      LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant())
    val todo = Todo(currentTodo.id, todoForm.name, currentTodo.version, currentTodo.createTimestamp, updateTime)
    return requireNotNull(todoDao.update(todo).entity.id) {
      "更新に失敗しました。"
    }
  }

  fun delete(id: Int): Unit {
    val currentTodo = findById(id)
    todoDao.delete(currentTodo)
  }
}