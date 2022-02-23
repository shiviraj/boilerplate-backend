package com.boardgames.uno.exceptions.exceptions

interface ServiceError {
    val errorCode: String
    val message: String
}

fun ServiceError.toBaseException() = BaseException(this)
