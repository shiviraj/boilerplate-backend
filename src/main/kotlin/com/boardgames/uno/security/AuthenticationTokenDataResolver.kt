package com.boardgames.uno.security

import com.boardgames.uno.domain.Player
import com.boardgames.uno.exceptions.AuthenticationDataException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthenticationTokenDataResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == Player::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return try {
            webRequest.getAttribute("player", 0) as? Player
        } catch (ex: Throwable) {
            throw AuthenticationDataException("Token parsing failed", ex)
        }
    }
}

