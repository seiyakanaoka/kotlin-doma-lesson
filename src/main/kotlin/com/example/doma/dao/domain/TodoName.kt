package com.example.doma.dao.domain

import org.seasar.doma.Domain

@Domain(valueType = String::class)
data class TodoName(val todoName: String) {
  fun getValue(): String {
    return todoName
  }
}