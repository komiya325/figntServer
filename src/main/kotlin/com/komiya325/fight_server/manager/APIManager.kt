package com.komiya325.fight_server.manager

import com.komiya325.fight_server.API.RegisterAPI
import com.komiya325.fight_server.common.IApiAdapter
import com.komiya325.fight_server.constant.CommonApi
import org.springframework.stereotype.Component

@Component
class APIManager(
    // 1. Springから必要なAPIクラスを注入してもらう
    private val registerAPI: RegisterAPI
) {
    // APIクラスを格納するMap (参考コードの `m` に相当)
    private val apiMap = mutableMapOf<String, IApiAdapter>()

    // 2. クラスの初期化時にMapに登録する (参考コードのコンストラクタ内処理)
    init {
        apiMap[CommonApi.REGISTER_USER] = registerAPI

        // 新しいAPIを作ったらコンストラクタ引数に追加し、ここに追記します
        // apiMap[CommonApi.LOGIN] = loginAPI
    }

    /**
     * コマンド名からAPI実装クラスを取得する
     */
    fun getApi(apiName: String): IApiAdapter? {
        return apiMap[apiName]
    }
}