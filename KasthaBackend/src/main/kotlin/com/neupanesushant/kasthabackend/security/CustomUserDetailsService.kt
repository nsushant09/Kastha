package com.neupanesushant.kasthabackend.security

import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.model.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepo: UserRepo
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            userRepo.findByEmail(username) ?: throw UsernameNotFoundException("Username : $username not found")

        return User(user.email, user.password, mapRolesToAuthorities(user.roles))
    }

    private fun mapRolesToAuthorities(roles: List<Role>): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
}