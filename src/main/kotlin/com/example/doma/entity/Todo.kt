package com.example.doma.entity

import com.example.doma.listener.TodoListener
import org.seasar.doma.Entity
import org.seasar.doma.GeneratedValue
import org.seasar.doma.GenerationType
import org.seasar.doma.Id
import org.seasar.doma.OriginalStates
import org.seasar.doma.Version
import org.seasar.doma.jdbc.entity.NamingType
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = TodoListener::class, immutable = true)
data class Todo (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,
  val name: String,
  @Version
  val version: Int? = 1,
  val createTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant()),
  val updateTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Tokyo")).toInstant()),
)