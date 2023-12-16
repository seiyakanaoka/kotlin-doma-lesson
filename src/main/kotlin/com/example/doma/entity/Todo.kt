package com.example.doma.entity

import org.seasar.doma.Entity
import org.seasar.doma.GeneratedValue
import org.seasar.doma.GenerationType
import org.seasar.doma.Id
import org.seasar.doma.Version
import org.seasar.doma.jdbc.entity.NamingType
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
data class Todo (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int,
  val name: String,
  @Version
  val version: Int? = null,
  val createTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
  val updateTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
)