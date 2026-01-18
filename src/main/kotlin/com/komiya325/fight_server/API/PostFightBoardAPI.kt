package com.komiya325.fight_server.API

import com.komiya325.fight_server.DAO.BoardPostRequest
import com.komiya325.fight_server.DAO.FightBoardDAO
import com.komiya325.fight_server.common.EntityRespond
import com.komiya325.fight_server.common.ErrorCode
import com.komiya325.fight_server.common.IApiAdapter
import com.komiya325.fight_server.common.Respond
import com.komiya325.fight_server.constant.FightBoard
import com.komiya325.fight_server.constant.ParamKey
import com.komiya325.fight_server.util.Util
import org.springframework.stereotype.Service

@Service
class PostFightBoardAPI(private val fightBoardDAO: FightBoardDAO) : IApiAdapter {

    override fun execute(body: Map<String, Any>): Respond {
        val respond = Respond()

        val userId = Util.getStringParam(body, ParamKey.USER_ID)
        val name = Util.getStringParam(body, ParamKey.NAME)
        val postContent = Util.getStringParam(body, ParamKey.POST_CONTENT)
        val fightType = Util.getStringParam(body, ParamKey.FIGHT_TYPE)

        if (userId.isEmpty()) {
            respond.code = ErrorCode.WRONG_DATA_FORMAT
            respond.message = "user_id is required"
            return respond
        }

        if (name.isEmpty()) {
            respond.code = ErrorCode.WRONG_DATA_FORMAT
            respond.message = "name is required"
            return respond
        }

        if (postContent.isEmpty()) {
            respond.code = ErrorCode.WRONG_DATA_FORMAT
            respond.message = "post_content is required"
            return respond
        }

        if (!FightBoard.FightType.ALLOWED_TYPES.contains(fightType)) {
            respond.code = ErrorCode.WRONG_DATA_FORMAT
            respond.message = "Invalid fight_type. Allowed values: ${FightBoard.FightType.ALLOWED_TYPES.joinToString(", ")}"
            return respond
        }

        return try {
            val request = BoardPostRequest(
                userId = userId,
                name = name,
                postContent = postContent,
                fightType = fightType
            )

            val insertedId = fightBoardDAO.insertPost(request)

            val resultData = mapOf(
                "status" to "success",
                "inserted_id" to insertedId
            )
            EntityRespond(resultData)

        } catch (ex: Exception) {
            ex.printStackTrace()
            respond.code = ErrorCode.UNKNOWN_ERROR
            respond.message = ex.message ?: "Unknown Error"
            respond
        }
    }
}