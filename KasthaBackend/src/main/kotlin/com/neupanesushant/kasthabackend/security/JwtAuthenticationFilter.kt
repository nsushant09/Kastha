package com.neupanesushant.kasthabackend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(OncePerRequestFilter::class.java)

    @Autowired
    private lateinit var jwtHelper : JwtHelper

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestHeader = request.getHeader("Authorization")
        logger.info("Header : $requestHeader")

        var username : String? =  null
        var token : String? = null

        if(requestHeader != null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7)
            try{ username = this.jwtHelper.getUsernameFromToken(token)}
            catch (e : Exception){e.printStackTrace()}
        }

        if(username != null &&
            token != null &&
            SecurityContextHolder.getContext().authentication == null){
            val userDetails = this.userDetailsService.loadUserByUsername(username)
            val validateToken = this.jwtHelper.validateToken(token, userDetails)
            if(validateToken){
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }


}