package com.example.spring_security.controller.auth

import com.example.spring_security.securityConfig.UserDetailServiceImp
import com.example.spring_security.securityConfig.jwtConfig.JwtUtils
import com.example.spring_security.securityConfig.jwtConfig.model.JwtRequest
import com.example.spring_security.service.UserService
import com.example.spring_security.simpleResponce.ResponseObjectMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var jwtUtils: JwtUtils
    @Autowired
    lateinit var userDetailsService : UserDetailServiceImp

    val response = ResponseObjectMap()

    @Autowired
    lateinit var userService: UserService



    @PostMapping("/login")
    fun login (@RequestBody authenticationRequest: JwtRequest): MutableMap<String, Any> {

        println("Log:: read user from DB")

        val username: String = authenticationRequest.username
        val userDetails = userDetailsService.loadUserByUsername(username)

        //verify auth-role
        validateAuthenticate(userDetails.username!!, authenticationRequest.password)

        //generate new Token
        val jwt = jwtUtils.generateToken(userDetails)


        userService.deleteOldUserToken(username)

        //set user to redis
        userService.addUserRedis(jwt.token, userDetails)


        println("Token : ${jwt.token}")
        return response.responseObject(jwt)
    }


    //fun : Register new user
    //fun : Update User
    //fun : Set Role to user
    //fun : Remove Role from user
    //fun : Block User


    @Throws(Exception::class)
    private fun validateAuthenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", BadCredentialsException("Incorrect username or password"))
        }
    }


}