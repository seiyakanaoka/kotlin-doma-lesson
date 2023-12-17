# Kotlin Springboot（Gradle - Groovy）とDoma2の勉強用リポジトリ

## 環境構築手順（intellij IDEAを使用する想定）

### build.gradleの場合

#### intellijの設定
1. [こちら](https://doma.readthedocs.io/en/2.19.3/getting-started-idea/#id18)を参考に、inteliijの設定を行う
※「Generated Sources Root」という設定はしなかった

#### build.gradleの設定
1. 「apply plugin: 'kotlin-kapt'」を、plugins直下に記載する
2. buildファイルにSQLファイルがコピーされるように、以下のコードを、上記の設定の直下に記載する
```
// コンパイルより前にSQLファイルを出力先ディレクトリにコピーするために依存関係を逆転する
compileJava.dependsOn processResources

// SQLファイルなどリソースファイルの出力先ディレクトリをkaptに伝える
kapt {
	arguments {
		arg("doma.resources.dir", processResources.destinationDir)
	}
}
```
3. domaの依存関係を追加する
```
...
kapt "org.seasar.doma:doma-processor:2.53.1"
implementation "org.seasar.doma.boot:doma-spring-boot-starter:1.7.0"
implementation "org.seasar.doma:doma-kotlin:2.53.1" // <= これがなくても動いた（理由は不明）
```

### build.gradle.ktsの場合
build.gradleの設定で、SQLファイルのコピーの設定方法が違うだけ
```
// コンパイルより前にSQLファイルを出力先ディレクトリにコピーするために依存関係を逆転する
tasks.compileJava {
	dependsOn("processResources")
}

val compileKotlin: KotlinCompile by tasks

// SQLファイルなどリソースファイルの出力先ディレクトリをkaptに伝える
kapt {
	arguments {
		arg("doma.resources.dir", tasks.getByName<ProcessResources>("processResources").destinationDir)
	}
}

tasks.register("copyDomaResources",Sync::class){
	from("src/main/resources")
	into(compileKotlin.destinationDirectory)
	include("doma.compile.config")
	include("META-INF/**/*.sql")
	include("META-INF/**/*.script")
}
```

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
