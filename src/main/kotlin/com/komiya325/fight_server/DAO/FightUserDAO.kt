package com.komiya325.fight_server.DAO

import com.komiya325.fight_server.constant.FightUser
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.Date
import com.mongodb.MongoWriteException


data class RegistrationRequest(
    val userId: String,
    val name: String,
    val email: String
)

@Repository
class FightUserDAO(private val mongoTemplate: MongoTemplate) {

          //新規登録処理
    fun insertUser(request: RegistrationRequest): String? {
        val doc = Document().apply {
            append(FightUser.Field.USER_ID, request.userId)
            append(FightUser.Field.NAME, request.name)
            append(FightUser.Field.EMAIL, request.email)
            append(FightUser.Field.CREATED_AT, Date())
        }

        return try {
            // 書き込み実行
            mongoTemplate.getCollection(FightUser.COLLECTION_NAME).insertOne(doc)

            // 成功：自動生成された _id (ObjectId) を返す
            doc.getObjectId("_id").toString()

        } catch (e: MongoWriteException) {
            // エラーコード 11000 は Unique Index 違反 (重複)
            if (e.error.code == 11000) {
                println("--- [ERROR] DUPLICATE USER_ID: ${request.userId} ---")
                return "DUPLICATE" // または null を返して上位で判定させる
            }
            throw e // 重複以外の深刻なエラー（接続切れ等）はそのまま投げる
        }
    }
}