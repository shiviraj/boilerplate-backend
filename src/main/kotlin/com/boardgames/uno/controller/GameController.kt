package com.boardgames.uno.controller

import com.boardgames.uno.controller.view.GameRequest
import com.boardgames.uno.controller.view.GameResponse
import com.boardgames.uno.domain.Player
import com.boardgames.uno.service.GameService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/games")
class GameController(
    val gameService: GameService
) {

    @PostMapping("/start")
    fun initGame(@RequestBody gameRequest: GameRequest, player: Player?): Mono<GameResponse> {
        return gameService.initGame(gameRequest, player)
    }

    @GetMapping("/{gameId}/status")
    fun getStatus(@PathVariable gameId: String, player: Player): Mono<GameResponse> {
        return gameService.getStatus(gameId).map { GameResponse.from(it, player) }
    }

    @GetMapping("/{gameId}/start")
    fun startGame(@PathVariable gameId: String, player: Player): Mono<GameResponse> {
        return gameService.startGame(gameId, player).map { GameResponse.from(it, player) }
    }
}
