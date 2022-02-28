package com.boardgames.uno.service

import com.boardgames.uno.gateway.BoardGameGateway
import org.springframework.stereotype.Service

@Service
class UserService(
    val idGeneratorService: IdGeneratorService,
    val boardGameGateway: BoardGameGateway
) {


}

