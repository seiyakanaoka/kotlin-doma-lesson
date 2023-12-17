package com.example.doma.service

import com.example.doma.dao.TodoDao
import com.example.doma.entity.Todo
import com.example.doma.form.TodoForm
import com.example.doma.form.TodoForms
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
@RequiredArgsConstructor
class TodoService(private val todoDao: TodoDao) {
  fun findAll(name: String?): List<Todo> {
//    val option: SelectOptions = SelectOptions.get().offset(5).limit(10) <= offsetやlimit, countを指定できる
    return todoDao.findAll(name)
  }

  fun findById(id: Int): Todo {
    println("aaaa : ${todoDao.findById(id)}")
    return todoDao.findById(id)
  }

  fun insert(todoForm: TodoForm): Int {
    val todo = Todo(null, todoForm.name)
    return requireNotNull(todoDao.insert(todo).entity.id) {
      "登録に失敗しました。"
    }
  }

  fun update(todoForm: TodoForm): Int {
    val currentTodo = requireNotNull(todoForm.id?.let { findById(it) })
    val updateTime = Date.from(
      LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant()
    )
    val todo = Todo(currentTodo.id, todoForm.name, currentTodo.version, currentTodo.createTimestamp, updateTime)
    return requireNotNull(todoDao.update(todo).entity.id) {
      "更新に失敗しました。"
    }
  }

  fun delete(id: Int): Unit {
    val currentTodo = findById(id)
    todoDao.delete(currentTodo)
  }

  // -------------------- バッチ系メソッド --------------------

  fun batchInsert(todoForms: TodoForms): List<Int> {
    val todos = todoForms.todos.map { it -> Todo(null, it.name) }
    return todoDao.batchInsert(todos).entities.map { it -> requireNotNull(it.id) }
  }

  fun batchUpdate(todoForms: TodoForms): List<Int> {
    val ids = todoForms.todos.map { it -> requireNotNull(it.id) }
    val currentTodos = todoDao.findByTodos(ids)
    if (currentTodos.size != ids.size) throw RuntimeException("指定されたIDは存在しません。")
    val updateTime = Date.from(
      LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant()
    )
    val todos = currentTodos.mapNotNull { currentTodo ->
      todoForms.todos.find { todoForm -> todoForm.id == currentTodo.id }
        .let { findTodo ->
          Todo(
            findTodo?.id,
            requireNotNull(findTodo?.name),
            currentTodo.version,
            currentTodo.createTimestamp,
            updateTime
          )
        }
    }
    return todoDao.batchUpdate(todos).entities.map { it -> requireNotNull(it.id) }
  }

  fun batchDelete(todoForms: TodoForms): Unit {
    val ids = todoForms.todos.map { it -> requireNotNull(it.id) }
    val currentTodos = todoDao.findByTodos(ids)
    if (currentTodos.size != ids.size) throw RuntimeException("指定されたIDは存在しません。")
    todoDao.batchDelete(currentTodos)
  }
}