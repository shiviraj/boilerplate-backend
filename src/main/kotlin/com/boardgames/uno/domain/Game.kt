package com.boardgames.uno.domain

import com.boardgames.uno.controller.view.RoomType
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

const val GAME_COLLECTION = "games"

@TypeAlias("Game")
@Document(GAME_COLLECTION)
data class Game(
    @Id
    var id: ObjectId? = null,
    @Indexed(unique = true)
    val roomNo: String,
    val type: RoomType,
    var state: State = State.CREATED,
    var host: Player? = null,
    val players: MutableSet<Player> = mutableSetOf()
) {
    fun addPlayer(player: Player): Game {
        this.players.add(player)
        if (host == null && player.type != PlayerType.BOT) host = player
        return this
    }

    fun addBots(bots: String): Game {
        players.addAll(Player.createBots(bots.toInt()))
        return this
    }

    fun updateState(): Game {
        this.state = state.getNext()
        return this
    }
}


enum class State(private val sequence: Int) {
    CREATED(0),
    STARTED(1),
    FINISHED(2);

    fun getNext(): State {
        return when (sequence) {
            0 -> STARTED
            1 -> FINISHED
            else -> FINISHED
        }
    }
}
