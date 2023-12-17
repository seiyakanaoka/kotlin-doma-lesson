package com.example.doma.dao

import com.example.doma.config.MySQLConfig
import com.example.doma.entity.Todo
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.BatchResult
import org.seasar.doma.jdbc.Result


@ConfigAutowireable
@Dao(config = MySQLConfig::class)
interface TodoDao {
  @Select
  fun findAll(name: String?): List<Todo>

  @Select
  fun findById(id: Int): Todo

  @Insert
  fun insert(todo: Todo): Result<Todo>

  @Update
  fun update(todo: Todo): Result<Todo>

  @Delete
  fun delete(todo: Todo): Result<Todo>

  // -------------------- バッチ系メソッド --------------------

  @BatchInsert
  fun batchInsert(todos: List<Todo>): BatchResult<Todo>

  @BatchUpdate
  fun batchUpdate(todos: List<Todo>): BatchResult<Todo>

  @BatchDelete
  fun batchDelete(todos: List<Todo>): BatchResult<Todo>

  // -------------------- Utilメソッド --------------------
  @Select
  fun findByTodos(ids: List<Int>): List<Todo>
}
