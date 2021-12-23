package com.example.spring_security.service

import com.example.spring_security.RedisConfig.UserRedisRepository
import com.example.spring_security.securityConfig.UserDetailsPrincipal
import com.example.spring_security.securityConfig.jwtConfig.JwtUtils
import com.example.spring_security.securityConfig.model.Users
import com.example.spring_security.securityConfig.model.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository : UserRepository
    @Autowired
    lateinit var userRedisRepository: UserRedisRepository
    @Autowired
    lateinit var jwtUtils: JwtUtils

    //fun : Register new user
    //fun : Update User
    //fun(username,roleName) : Set Role to user
    //fun : Remove Role from user
    //fun : Block User

    fun deleteOldUserToken (username: String){

        val currentToken =  userRedisRepository.getTokenByUserName(username)
        userRedisRepository.deleteAuth(currentToken)
        userRedisRepository.removeSecUser(username)
    }

    fun addUserRedis (token:String, user: UserDetailsPrincipal){
        userRedisRepository.setAuth(token, user,jwtUtils.JWT_TOKEN_VALIDITY)
    }

    fun addSecUserRedis (username: String, token: String) {
        //userRedisRepository.setSecUser()
    }

    fun saveUser (users:Users): Users {
        return userRepository.save(users)
    }


    fun getUserFromRedis (token: String) : UserDetailsPrincipal?{
        return userRedisRepository.getAuth(token)
    }

    fun getUser(username:String) : Users?{
        return userRepository.findByUsername(username)
    }


}