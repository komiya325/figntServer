package com.komiya325.fight_server.constant

object FightBoard {
    const val COLLECTION_NAME = "fight_board"

    object Field {
        const val ID = "_id"
        const val USER_ID = "user_id"
        const val NAME = "name"
        const val POST_CONTENT = "post_content"
        const val FIGHT_TYPE = "fight_type"
        const val CREATED_AT = "created_at"
    }

    object FightType {
        const val KICK = "kick"
        const val BOXING = "boxing"
        const val MMA = "mma"

        val ALLOWED_TYPES = listOf(KICK, BOXING, MMA)
    }
}