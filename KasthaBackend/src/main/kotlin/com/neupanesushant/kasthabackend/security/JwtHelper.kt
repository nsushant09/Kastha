package com.neupanesushant.kasthabackend.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.yaml.snakeyaml.resolver.Resolver
import java.util.*

@Component
object JwtHelper {
    private const val JWT_TOKEN_VALIDITY = 5 * 60 * 60
    private const val SECRET = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf"

    fun getUsernameFromToken(token : String) =
        getClaimFromToken(token, Claims::getSubject)

    fun getExpirationDateFromToken(token : String) =
        getClaimFromToken(token, Claims::getExpiration)
    fun <T> getClaimFromToken(token : String, claimsResolver: (Claims) -> T) : T{
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.invoke(claims)
    }
    private fun getAllClaimsFromToken(token : String): Claims =
        Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(token).body

    private fun isTokenExpired(token : String) =
        getExpirationDateFromToken(token).before(Date())

    fun generateToken(authentication: Authentication) : String =
        doGenerateToken(subject = authentication.name)

    private fun doGenerateToken(claims : Map<String, Any> = hashMapOf(), subject : String) : String =
        Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()

    fun validateToken(token : String, userDetails : UserDetails) : Boolean {
        val username = getUsernameFromToken(token)
        return username.equals(userDetails.username) && !isTokenExpired(token)
    }
}