package com.komiya325.fight_server.DAO

import com.komiya325.fight_server.constant.FightBoard
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class BoardPostRequest(
    val userId: String,
    val name: String,
    val postContent: String,
    val fightType: String
)

@Repository
class FightBoardDAO(private val mongoTemplate: MongoTemplate) {

    private val jstZone = ZoneId.of("Asia/Tokyo")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun insertPost(request: BoardPostRequest): String {
        val jstNow = LocalDateTime.now(jstZone)
        val createdAtString = jstNow.format(dateFormatter)

        val doc = Document().apply {
            append(FightBoard.Field.USER_ID, request.userId)
            append(FightBoard.Field.NAME, request.name)
            append(FightBoard.Field.POST_CONTENT, request.postContent)
            append(FightBoard.Field.FIGHT_TYPE, request.fightType)
            append(FightBoard.Field.CREATED_AT, createdAtString)
        }

        mongoTemplate.getCollection(FightBoard.COLLECTION_NAME).insertOne(doc)

        return doc.getObjectId(FightBoard.Field.ID).toString()
    }
}