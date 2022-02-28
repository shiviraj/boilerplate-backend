package com.boardgames.uno.exceptions.error_code

import com.boardgames.uno.exceptions.exceptions.ServiceError


enum class GameError(override val errorCode: String, override val message: String) : ServiceError {
    Game600("BLOG-606", "Unauthorized User"),
    Game603("BLOG-603", "Bad request"),
    Game604("BLOG-604", "Secret Not Found"),
    Game605("BLOG-605", "Post Not Found"),
}
