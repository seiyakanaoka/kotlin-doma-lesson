package com.example.doma.dao

import com.example.doma.config.MySQLConfig
import com.example.doma.entity.Todo
import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable

@ConfigAutowireable
@Dao(config = MySQLConfig::class)
interface TodoDao {
  @Select
  fun findAll(name: String?): List<Todo>
}
