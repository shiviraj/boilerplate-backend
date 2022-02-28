package com.boardgames.uno.domain

data class Player(
    val name: String,
    val userId: String,
    val profile: String = "",
    val username: String = "",
    val type: PlayerType = PlayerType.PLAYER
) {
    private var token: String = ""

    fun updateToken(token: String): Player {
        this.token = token
        return this
    }

    fun getPlayerToken() = token

    companion object {
        fun createBots(number: Int): List<Player> {
            var botId = 1
            val list = mutableListOf<Player>()
            while (botId <= number) {
                list.add(Player(name = "Player $botId", userId = "bot$botId", type = PlayerType.BOT))
                botId++
            }
            return list
        }
    }
}


enum class PlayerType {
    PLAYER,
    BOT
}
