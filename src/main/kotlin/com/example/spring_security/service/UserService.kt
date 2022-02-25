package com.example.spring_security.service

import com.example.spring_security.configuration.RedisConfig.UserRedisRepository
import com.example.spring_security.configuration.securityConfig.UserDetailsPrincipal
import com.example.spring_security.configuration.securityConfig.jwtConfig.JwtUtils
import com.example.spring_security.configuration.securityConfig.model.Users
import com.example.spring_security.configuration.securityConfig.model.repo.UserRepository
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


//############### Redis Operation ###############

    /**
     * @Delete current token
     *
     * this process is delete current token & remove token from list token of this user
     */
    fun deleteOldUserToken (username: String){

        userRedisRepository.getTokenByUserName(username)?.let {
            userRedisRepository.deleteAuth(it)
            userRedisRepository.removeTokenFromSecUser(username)
        }
    }

    fun addUserRedis (token:String, user: UserDetailsPrincipal){
        userRedisRepository.setAuth(token, user,jwtUtils.JWT_TOKEN_VALIDITY)
        userRedisRepository.addTokenToSecUser(user.username!!,token,jwtUtils.JWT_TOKEN_VALIDITY)
    }

    fun getUserFromRedis (token: String) : UserDetailsPrincipal?{
        return userRedisRepository.getAuth(token)
    }



//############### DataBase Operation ###############

    fun getUser(username:String) : Users?{
        return userRepository.findByUsername(username)
    }

    fun saveUser (users:Users): Users {
        return userRepository.save(users)
    }

    fun addRoleToUer (username: String, roleName:String) {

    }
}
