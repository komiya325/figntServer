package com.komiya325.fight_server.DAO

import com.komiya325.fight_server.constant.FightUser
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.util.Date

// ここも変更なしでOK
data class RegistrationRequest(
    val userId: String,
    val name: String,
    val email: String
)

@Repository
class FightUserDAO(private val mongoTemplate: MongoTemplate) {

    fun insertUser(request: RegistrationRequest): String {
        // ここで APIから渡された request.userId (UUID) をセットしています
        val doc = Document().apply {
            append(FightUser.Field.USER_ID, request.userId) // ← これがAPIで作ったUUID
            append(FightUser.Field.NAME, request.name)
            append(FightUser.Field.EMAIL, request.email)
            append(FightUser.Field.CREATED_AT, Date())
        }

        // insertOneした瞬間に、docの中に "_id" (ObjectId) が自動で生成されます
        mongoTemplate.getCollection(FightUser.COLLECTION_NAME).insertOne(doc)

        // 必要であれば _id を返しますが、今回はAPI側でUUIDを持っているので
        // この戻り値は使わなくても問題ありません
        return doc.getObjectId(FightUser.Field.ID).toString()
    }
}