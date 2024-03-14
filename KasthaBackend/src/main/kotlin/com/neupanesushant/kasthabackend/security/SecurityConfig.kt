package com.neupanesushant.kasthabackend.security

import jakarta.el.Expression
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val userDetailsService: CustomUserDetailsService
) {
    @Autowired
    private lateinit var point: JwtAuthenticationEntryPoint

    @Autowired
    private lateinit var filter: JwtAuthenticationFilter

    @Bean
    @Throws(Exception::class)
    public fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**", "/api/mail/**").permitAll()
                it.anyRequest().authenticated()
            }
            .exceptionHandling { it.authenticationEntryPoint(point) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @get:Bean
    val passwordEncoder get() = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(builder: AuthenticationConfiguration): AuthenticationManager {
        return builder.authenticationManager
    }
}