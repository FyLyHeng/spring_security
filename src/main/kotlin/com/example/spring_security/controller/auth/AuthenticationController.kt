package com.example.spring_security.controller.auth

import com.example.spring_security.securityConfig.UserDetailServiceImp
import com.example.spring_security.securityConfig.jwtConfig.JwtUtils
import com.example.spring_security.securityConfig.jwtConfig.model.JwtRequest
import com.example.spring_security.securityConfig.jwtConfig.model.JwtResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/auth")
class AuthenticationController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var jwtUtils: JwtUtils
    @Autowired
    lateinit var userDetailsService : UserDetailServiceImp



    @PostMapping("/login")
    fun login (@RequestBody authenticationRequest: JwtRequest): ResponseEntity<JwtResponse> {


        val username: String = authenticationRequest.username
        val userDetails = userDetailsService.loadUserByUsername(username)

        authenticate(userDetails.username!!, authenticationRequest.password)

        val token = jwtUtils.generateToken(userDetails)

        println("Gen-token : $token")

        return ResponseEntity.ok(JwtResponse(token, Date()))
    }


    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", BadCredentialsException("Incorrect username or password"))
        }
    }


}