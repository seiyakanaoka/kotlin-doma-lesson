package com.example.doma.controller

import com.example.doma.entity.Todo
import com.example.doma.service.TodoService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class TodoController(private val todoService: TodoService) {
  @GetMapping("/todos")
  fun findAll(@RequestParam(name = "name", required = false) name: String): List<Todo?>? {
    return todoService.findAll(name)
  }
}