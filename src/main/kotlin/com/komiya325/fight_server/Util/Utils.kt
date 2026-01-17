package com.komiya325.fight_server.util

object Util {

    /**
     * Map(JSON)からStringパラメータを安全に取得する
     * 存在しない場合やnullの場合は空文字を返す
     */
    fun getStringParam(body: Map<String, Any>, key: String): String {
        val value = body[key]
        return value?.toString() ?: ""
    }

    /**
     * Map(JSON)からIntパラメータを安全に取得する
     * (必要であれば追加)
     */
    fun getIntParam(body: Map<String, Any>, key: String): Int {
        val value = body[key]
        return if (value is Int) value else 0
    }
}