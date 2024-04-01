package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kastha.domain.model.KeyValue
import com.neupanesushant.kasthabackend.core.repo.RoleRepo
import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.data.dtomodel.AuthResponseDTO
import com.neupanesushant.kasthabackend.data.dtomodel.LoginDTO
import com.neupanesushant.kasthabackend.data.dtomodel.RegisterDTO
import com.neupanesushant.kasthabackend.data.dtomodel.UserDTO
import com.neupanesushant.kasthabackend.data.model.BaseResponse
import com.neupanesushant.kasthabackend.data.model.User
import com.neupanesushant.kasthabackend.security.JwtHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Collections
import javax.management.relation.RoleNotFoundException

@RestController
@RequestMapping("/api/auth")
class AuthController @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userRepo: UserRepo,
    private val roleRepo: RoleRepo,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtHelper
) {

    @PostMapping("/login")
    private fun login(
        @RequestBody loginDTO: LoginDTO
    ): ResponseEntity<AuthResponseDTO> {
        val authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginDTO.email,
                    loginDTO.password
                )
            )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtGenerator.generateToken(authentication)
        val user = userRepo.findByEmail(loginDTO.email) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(AuthResponseDTO(userId = user.id, accessToken = token))
    }

    @PostMapping("/register")
    private fun register(@RequestBody registerDTO: RegisterDTO): ResponseEntity<KeyValue<String, String>> {
        if (userRepo.existsByEmail(registerDTO.email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

        val roles = roleRepo.findByName("USER") ?: throw RoleNotFoundException("Role user not found")
        val user = User(
            -1,
            registerDTO.firstName,
            registerDTO.lastName,
            registerDTO.email,
            passwordEncoder.encode(registerDTO.password),
            registerDTO.gender,
            registerDTO.location,
            Collections.singletonList(roles)
        )
        userRepo.save(user)
        return ResponseEntity.ok(KeyValue(registerDTO.email, registerDTO.password))
    }

    @PostMapping("/loginValidation")
    private fun loginValidation(
        @RequestBody loginDTO: LoginDTO
    ): ResponseEntity<out Any> {
        val user = userRepo.findByEmail(loginDTO.email) ?: return ResponseEntity.badRequest()
            .body("Please provide a registered email")
        if (!passwordEncoder.matches(loginDTO.password, user.password)) return ResponseEntity.badRequest()
            .body("The provided password does not match")
        return ResponseEntity.ok(BaseResponse(success = true, "Valid Details"))
    }
}
