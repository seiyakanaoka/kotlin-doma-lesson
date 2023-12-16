package com.example.doma.service

import com.example.doma.dao.TodoDao
import com.example.doma.entity.Todo
import com.example.doma.form.TodoForm
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

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
    val currentTodo = todoForm.id?.let { findById(it) }
    val todo = Todo(currentTodo?.id, todoForm.name)
    return requireNotNull(todoDao.update(todo).entity.id) {
      "更新に失敗しました。"
    }
  }

  fun delete(id: Int): Unit {
    val currentTodo = findById(id)
    todoDao.delete(currentTodo)
  }
}