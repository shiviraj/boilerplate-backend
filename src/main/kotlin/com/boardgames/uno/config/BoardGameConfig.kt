package com.boardgames.uno.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("uno.board-game")
data class BoardGameConfig(
    val baseUrl: String,
    val path: String,
)
