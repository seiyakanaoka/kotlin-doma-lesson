package com.example.doma.listener

import com.example.doma.entity.Todo
import org.seasar.doma.jdbc.entity.EntityListener
import org.seasar.doma.jdbc.entity.PostUpdateContext
import org.seasar.doma.jdbc.entity.PreUpdateContext

class TodoListener : EntityListener<Todo> {
  override fun preUpdate(todo: Todo, context: PreUpdateContext<Todo>) {
    println("*******************************")
    println(" ")
    println("更新前に呼び出し")
    println(" ")
    println("*******************************")
  }

  override fun postUpdate(todo: Todo?, context: PostUpdateContext<Todo>?) {
    println("*******************************")
    println(" ")
    println("更新後に呼び出し")
    println(" ")
    println("*******************************")
  }
}