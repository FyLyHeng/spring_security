package com.example.spring_security.configuration.securityConfig.jwtConfig

import com.example.spring_security.configuration.securityConfig.jwtConfig.model.JwtResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function



@Component
class JwtUtils {
    val SECRET_KEY = "secret"
    val JWT_TOKEN_VALIDITY: Long = 30 * 24 * 60 * 60

    fun extractUsername(token: String): String {
        return extractClaim(token, Function { it.subject })
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Function { it.expiration })
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean? {
        return extractExpiration(token).before(Date())
    }



    fun generateToken(userDetails: UserDetails): JwtResponse {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, userDetails.username)
    }



    private fun createToken(claims: Map<String, Any>, subject: String): JwtResponse {
        val expire = Date(System.currentTimeMillis() +  JWT_TOKEN_VALIDITY)
         val token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()

        return JwtResponse(token,expire)
    }


    fun validateToken(token: String, userDetails: UserDetails): Boolean? {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)!!
    }
}
