package com.boardgames.uno.service

import com.boardgames.uno.controller.view.GameRequest
import com.boardgames.uno.controller.view.GameResponse
import com.boardgames.uno.controller.view.Mode
import com.boardgames.uno.controller.view.RoomAction
import com.boardgames.uno.domain.Game
import com.boardgames.uno.domain.Player
import com.boardgames.uno.domain.State
import com.boardgames.uno.exceptions.error_code.GameError
import com.boardgames.uno.exceptions.exceptions.ForbiddenException
import com.boardgames.uno.gateway.BoardGameGateway
import com.boardgames.uno.repository.GameRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GameService(
    val idGeneratorService: IdGeneratorService,
    val boardGameGateway: BoardGameGateway,
    val gameRepository: GameRepository
) {
    fun initGame(gameRequest: GameRequest, player: Player?): Mono<GameResponse> {
        return findOrCreateRoom(gameRequest).flatMap { game ->
            findOrCreatePlayer(player, gameRequest).flatMap { player ->
                save(game.addPlayer(player)).map {
                    GameResponse.from(game, player, player.getPlayerToken())
                }
            }
        }
    }

    fun getStatus(gameId: String) = gameRepository.findByRoomNo(gameId)

    fun startGame(gameId: String, player: Player): Mono<Game> {
        return getStatus(gameId).flatMap {
            if (it.host?.userId == player.userId && it.state == State.CREATED) {
                save(it.updateState())
            } else {
                Mono.error(ForbiddenException(GameError.Game600))
            }
        }
    }

    private fun findOrCreateRoom(gameRequest: GameRequest): Mono<Game> {
        return if (gameRequest.mode == Mode.COMPUTER || gameRequest.room == RoomAction.CREATE)
            createRoom(gameRequest)
        else joinRoom(gameRequest.roomNo)
    }

    private fun findOrCreatePlayer(player: Player?, gameRequest: GameRequest): Mono<Player> {
        return if (player == null || player.name != gameRequest.name)
            boardGameGateway.createPlayer(gameRequest.name, player?.userId)
        else Mono.just(player)
    }

    private fun joinRoom(roomNo: String): Mono<Game> = gameRepository.findByRoomNo(roomNo)

    private fun createRoom(gameRequest: GameRequest): Mono<Game> {
        return idGeneratorService.generateId(IdType.GameId).map { Game(roomNo = it, type = gameRequest.type) }
            .map {
                if (gameRequest.mode == Mode.COMPUTER) it.addBots(gameRequest.bots)
                else it
            }
    }

    private fun save(game: Game) = gameRepository.save(game)
}
// token =>  Room No, player Id,
