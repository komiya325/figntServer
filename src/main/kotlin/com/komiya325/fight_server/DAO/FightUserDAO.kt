package com.komiya325.fight_server.DAO

import com.komiya325.fight_server.constant.FightUser
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.Date


data class RegistrationRequest(
    val name: String,
    val email: String
)

@Repository
class FightUserDAO(private val mongoTemplate: MongoTemplate) {

          //新規登録処理
    fun insertUser(request: RegistrationRequest): String {
        val doc = Document().apply {
            append(FightUser.Field.NAME, request.name)
            append(FightUser.Field.EMAIL, request.email)
            append(FightUser.Field.CREATED_AT, Date())
        }

        // 書き込み実行
        mongoTemplate.getCollection(FightUser.COLLECTION_NAME).insertOne(doc)

        // 成功：自動生成された _id (ObjectId) を user_id として返す
        return doc.getObjectId("_id").toString()
    }
}