package com.example.doma.config

import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.dialect.Dialect
import javax.sql.DataSource

class MySQLConfig() : Config {

  private var dialect: Dialect? = null

  private var dataSource: DataSource? = null

  override fun getDialect(): Dialect? {
    return dialect
  }

  fun setDialect(dialect: Dialect?) {
    this.dialect = dialect
  }

  override fun getDataSource(): DataSource? {
    return dataSource
  }

  fun setDataSource(dataSource: DataSource?) {
    this.dataSource = dataSource
  }
}