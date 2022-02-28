package com.boardgames.uno.controller.view

import com.boardgames.uno.domain.Game
import com.boardgames.uno.domain.Player
import com.boardgames.uno.domain.PlayerType

data class GameResponse(val game: Game, val player: PlayerView, val token: String?) {
    companion object {
        fun from(game: Game, player: Player, token: String? = null): GameResponse {
            return GameResponse(game, PlayerView.from(player), token)
        }
    }
}

data class PlayerView(
    val name: String,
    val userId: String,
    val profile: String = "",
    val username: String = "",
    val type: PlayerType
) {
    companion object {
        fun from(player: Player): PlayerView {
            return PlayerView(
                name = player.name,
                userId = player.userId,
                profile = player.profile,
                username = player.username,
                type = player.type
            )
        }
    }
}
