package com.boardgames.uno.gateway

import com.boardgames.uno.config.BoardGameConfig
import com.boardgames.uno.domain.Player
import com.boardgames.uno.service.logOnError
import com.boardgames.uno.service.logOnSuccess
import com.boardgames.uno.webClient.WebClientWrapper
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BoardGameGateway(
    private val webClientWrapper: WebClientWrapper,
    private val boardGameConfig: BoardGameConfig,
) {
    fun authorizeUser(token: String): Mono<Player> {
        return webClientWrapper.get(
            baseUrl = boardGameConfig.baseUrl,
            path = "${boardGameConfig.path}/authorize",
            returnType = Player::class.java,
            headers = mapOf(
                HttpHeaders.ACCEPT to "application/json",
                HttpHeaders.AUTHORIZATION to token
            )
        )
            .logOnSuccess("Successfully authorized user")
            .logOnError("Failed to authorize user")
    }

    fun createPlayer(name: String, userId: String?): Mono<Player> {
        return webClientWrapper.post(
            baseUrl = boardGameConfig.baseUrl,
            path = boardGameConfig.path,
            returnType = PlayerCreationResponse::class.java,
            body = mapOf("name" to name, "userId" to userId),
            headers = mapOf(
                HttpHeaders.ACCEPT to "application/json"
            )
        )
            .map {
                it.user.updateToken(it.token)
            }
            .logOnSuccess("Successfully created new user")
            .logOnError("Failed to create new user")
    }
}

data class PlayerCreationResponse(val token: String, val user: Player)
