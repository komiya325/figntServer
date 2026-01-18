# プロジェクトメモ


## このプロジェクトの目的
このプロジェクトは格闘技について自由に語るためのアプリのバックエンド部分を担当してます。


## 概要(技術)
このプロジェクトは Spring Boot 4.0.1 + Kotlin で構築されています。
MongoDB Atlas への接続に関して、特有のトラブルを回避するためのカスタム構成を採用しています。

## MongoDB 接続に関する重要事項
- **接続方式**: `application.properties` による自動構成ではなく、`MongoConfig.kt` (または `FightServerApplication.kt` 内の `@Configuration`) による明示的な Bean 定義を使用しています。
- **理由**: 環境によって `spring.data.mongodb.uri` が無視され、デフォルトの `localhost:27017` にフォールバックしたり、データベースが強制的に `test` に設定されたりする問題が発生したためです。
- **データベース名**: `fight` に固定されています。

## セキュリティ
- 認証情報（接続URI）は `src/main/resources/application-secret.properties` に記述されています。
- このファイルは `.gitignore` に登録されており、GitHub には公開されません。
- ローカル開発時は、このファイルを作成し `mongodb.atlas.uri=...` を定義する必要があります。

## 依存関係
- Spring Boot 4.0.1
- MongoDB Java Driver 5.6.2

## クエリの実装方針

- **Filtersの利用**: クエリ（検索条件）の構築には、Spring Data MongoDB の `Query/Criteria` ではなく、**MongoDB Java Driver の `com.mongodb.client.model.Filters`** を優先的に使用します。
- **実装例**:
  ```kotlin
  import com.mongodb.client.model.Filters.*
  
  // 例: usernameが一致し、かつ status が "active" のものを検索
  val filter = and(eq("username", "ochi130325"), eq("status", "active"))
  collection.find(filter)
  
##開発ルール

- ハードコーディングはないようにする。
- 新規ファイルはkotlinで作成する
- 

