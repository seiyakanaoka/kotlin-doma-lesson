package com.example.doma.controller

import com.example.doma.entity.Todo
import com.example.doma.form.TodoForm
import com.example.doma.service.TodoService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
  fun post(@RequestBody todoForm: TodoForm): Int {
    return todoService.post(todoForm)
  }

  @PutMapping("/todo/{id}")
  fun update(@RequestBody todoForm: TodoForm): Int {
    return todoService.update(todoForm)
  }

  @DeleteMapping("/todo/{id}")
  fun delete(@PathVariable id: Int): Unit {
    todoService.delete(id)
  }
}