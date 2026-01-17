package com.komiya325.fight_server.handler

import com.komiya325.fight_server.common.ErrorCode
import com.komiya325.fight_server.common.Respond
import com.komiya325.fight_server.constant.ParamKey
import com.komiya325.fight_server.manager.APIManager
import com.komiya325.fight_server.util.Util
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class RequestHandler(private val apiManager: APIManager) {

    @PostMapping("/request")
    fun handle(@RequestBody body: Map<String, Any>): Respond {

        // 1. JSONから "api" (API名) を取得
        val api = Util.getStringParam(body, ParamKey.API)

        // 2. Managerから該当するAPIクラスを取得
        val apiAdapter = apiManager.getApi(api)

        // 3. 存在すれば実行、なければエラー
        return if (apiAdapter != null) {
            apiAdapter.execute(body)
        } else {
            val error = Respond()
            error.code = ErrorCode.UNKNOWN_ERROR // または "API NOT FOUND" 用のコード
            error.message = "Unknown API Command: $api"
            error
        }
    }
}