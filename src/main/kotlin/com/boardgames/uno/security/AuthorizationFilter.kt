package com.boardgames.uno.security

import com.boardgames.uno.gateway.BoardGameGateway
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AuthorizationFilter(val boardGameGateway: BoardGameGateway) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI == "/games/start") {
            authorizeUser(request)
        } else {
            val token = request.getHeader(HttpHeaders.AUTHORIZATION).orEmpty()
            val player = boardGameGateway.authorizeUser(token).block()
            if (player != null) {
                authorizeUser(request)
                request.setAttribute("player", player)
            } else {
                SecurityContextHolder.getContext().authentication = null
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun authorizeUser(request: HttpServletRequest) {
        val authenticationToken = UsernamePasswordAuthenticationToken("username", "userId", emptyList())
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }
}
