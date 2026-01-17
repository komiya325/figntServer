package com.komiya325.fight_server.common

/**
 * すべてのAPIクラスが実装すべきインターフェース
 */
interface IApiAdapter {
    fun execute(body: Map<String, Any>): Respond
}



