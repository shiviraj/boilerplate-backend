package com.boardgames.uno.repository

import com.boardgames.uno.domain.Game
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface GameRepository : ReactiveCrudRepository<Game, String> {
    fun findByRoomNo(roomNo: String): Mono<Game>
}
