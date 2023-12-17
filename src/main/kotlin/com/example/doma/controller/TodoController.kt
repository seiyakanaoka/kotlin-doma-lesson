package com.example.doma.controller

import com.example.doma.entity.Todo
import com.example.doma.form.TodoForm
import com.example.doma.form.TodoForms
import com.example.doma.service.TodoService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
class TodoController(private val todoService: TodoService) {
  @GetMapping("/todos")
  fun findAll(@RequestParam(name = "name", required = false) name: String): List<Todo?>? {
    return todoService.findAll(name)
  }

  @GetMapping("/todo/{id}")
  fun findById(@PathVariable id: Int): Todo {
    return todoService.findById(id)
  }

  @PostMapping("/todo")
  fun insert(@RequestBody todoForm: TodoForm): Int {
    return todoService.insert(todoForm)
  }

  @PutMapping("/todo")
  fun update(@RequestBody todoForm: TodoForm): Int {
    return todoService.update(todoForm)
  }

  @DeleteMapping("/todo/{id}")
  fun delete(@PathVariable id: Int): Unit {
    todoService.delete(id)
  }

  @PostMapping("/todos")
  fun batchInsert(@RequestBody todoForms: TodoForms): List<Int> {
    return todoService.batchInsert(todoForms)
  }

  @PutMapping("/todos")
  fun batchUpdate(@RequestBody todoForms: TodoForms): List<Int> {
    return todoService.batchUpdate(todoForms)
  }

  @DeleteMapping("/todos")
  fun delete(@RequestBody todoForms: TodoForms): Unit {
    todoService.batchDelete(todoForms)
  }
}