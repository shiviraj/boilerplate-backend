package com.boardgames.uno

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class AppContext(
    @Autowired applicationContext: ApplicationContext
) {

    init {
        com.boardgames.uno.AppContext.Companion.applicationContext = applicationContext
    }

    companion object {
        lateinit var applicationContext: ApplicationContext
    }
}

