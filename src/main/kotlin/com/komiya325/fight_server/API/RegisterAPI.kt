package com.komiya325.fight_server.API

import com.komiya325.fight_server.DAO.FightUserDAO
import com.komiya325.fight_server.DAO.RegistrationRequest
import com.komiya325.fight_server.common.EntityRespond
import com.komiya325.fight_server.common.ErrorCode
import com.komiya325.fight_server.common.IApiAdapter
import com.komiya325.fight_server.common.Respond
import com.komiya325.fight_server.constant.ParamKey
import com.komiya325.fight_server.util.Util
import org.springframework.stereotype.Service

@Service
class RegisterAPI(private val fightUserDAO: FightUserDAO) : IApiAdapter {

    override fun execute(body: Map<String, Any>): Respond {
        val respond = Respond()

        // 1. パラメータ取得 (name, emailのみ)
        val name = Util.getStringParam(body, ParamKey.NAME)
        val email = Util.getStringParam(body, ParamKey.EMAIL)

        // 2. バリデーション
        if (name.isEmpty()) {
            respond.code = ErrorCode.WRONG_DATA_FORMAT
            respond.message = "Name is empty"
            return respond
        }

        try {
            // 3. リクエストオブジェクト作成
            val request = RegistrationRequest(
                name = name,
                email = email
            )

            // 4. DBに保存し、MongoDBが自動生成した_idをuser_idとして取得
            val userId = fightUserDAO.insertUser(request)

            // 5. レスポンスに user_id を含めて返す
            val resultData = mapOf(
                "status" to "success",
                "user_id" to userId
            )
            return EntityRespond(resultData)

        } catch (ex: Exception) {
            ex.printStackTrace()
            respond.code = ErrorCode.UNKNOWN_ERROR
            respond.message = ex.message ?: "Unknown Error"
            return respond
        }
    }
}