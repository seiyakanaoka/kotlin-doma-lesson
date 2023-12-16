# Kotlin Springboot（Gradle - Groovy）とDoma2の勉強用リポジトリ

## 勉強メモ

### エンティティクラス
* __エンティティリスナー__
  * 挿入、更新、削除の処理の前、または後に処理を行うことができる（[公式](https://doma.seasar.org/reference/entity.html)）
  * 以下参照）更新前にprintlnを行う
```
class TodoListener : EntityListener<Todo> {
  override fun preUpdate(todo: Todo, context: PreUpdateContext<Todo>) {
      println("更新前に呼び出し")
  }
}

@Entity(listener = TodoListener::class)
data class Todo (val id: String)
```

* __バージョン__
  * 楽観的排他制御用のバージョンは @Version を注釈して示す。
  * @Versionを注釈できるフィールドの型は以下のいずれかでなければならない。
    * java.lang.Number のサブタイプ
    * java.lang.Number のサブタイプを値とする ドメインクラス
    * 上記のいずれかを要素の型とする java.util.Optional
    * OptionalInt
    * OptionalLong
    * OptionalDouble
    * 数値のプリミティブ型
* __テナント識別子__
  * テナント識別子は @TenantId を注釈して示す。
  * テナント識別子を注釈すると、更新や削除など __SQLが生成されるタイプのクエリ__ において、対応するカラムが検索条件としてWHERE句に含まれます。（以下参照）
```
@Entity(naming = NamingType.SNAKE_LOWER_CASE, immutable = true)
data class Todo (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,
  val name: String,
  @Version
  val version: Int? = 1,
  @TenantId
  val createTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
  val updateTimestamp: Date? = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
)

// 自動生成された更新SQL
update
  todo
set
  name = 'hoge'
  , update_timestamp = '1900-01-01 00:00:00.001'
  , version = 1 + 1
where
  id = 2
  and version = 4
  and create_timestamp = '1900-01-01 00:00:00.000' // <= エンティティのcreate_timestampの値が、where条件にて付与される
```
