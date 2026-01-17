package com.komiya325.fight_server.common

// 基本のレスポンス（コードとメッセージのみ）
open class Respond(
    var code: Int = ErrorCode.SUCCESS,
    var message: String = ""
)

// データ（JSONの中身）を返す時のレスポンス
class EntityRespond(val data: Any?) : Respond(ErrorCode.SUCCESS)