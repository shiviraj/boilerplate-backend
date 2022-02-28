package com.boardgames.uno.controller.view

data class GameRequest(
    val name: String,
    val mode: Mode,
    val bots: String,
    val type: RoomType,
    val room: RoomAction,
    val roomNo: String
)

enum class Mode {
    ONLINE,
    COMPUTER
}

enum class RoomType {
    PRIVATE,
    PUBLIC
}

enum class RoomAction {
    CREATE,
    JOIN
}


