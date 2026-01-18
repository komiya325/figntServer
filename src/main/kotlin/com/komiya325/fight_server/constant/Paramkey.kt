package com.komiya325.fight_server.constant

/**
 * リクエストパラメータのキー名を管理するクラス
 */
object ParamKey {
    // APIで受け取るJSONのキー
    const val API = "api"
    const val NAME = "name"
    const val EMAIL = "email"

    // 掲示板投稿用
    const val USER_ID = "user_id"
    const val POST_CONTENT = "post_content"
    const val FIGHT_TYPE = "fight_type"

    // エラーコードやその他のキーもここに追記できます
    const val ERROR_CODE = "error_code"
    const val DATA = "data"
}