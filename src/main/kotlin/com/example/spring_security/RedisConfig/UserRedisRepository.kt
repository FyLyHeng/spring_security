package com.example.spring_security.RedisConfig

import com.example.spring_security.securityConfig.UserDetailsPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit


@Repository
class UserRedisRepository {

    @Qualifier("redisTemplates")
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String,Any>
    private val AUTH_REDIS_KEY = "Security:Token"
    private val SECUSER_REDIS_KEY = "Security:username"


    fun deleteAuth (token: String){
        redisTemplate.opsForHash<String,String>().delete(AUTH_REDIS_KEY,token)
    }


    fun setAuth(token: String, auth: UserDetailsPrincipal, tokenExpire: Long) {
        redisTemplate.opsForHash<String, UserDetailsPrincipal>().put(AUTH_REDIS_KEY, token, auth)
        redisTemplate.expire(token, tokenExpire, TimeUnit.MILLISECONDS)

    }



    fun getAuth(token: String): UserDetailsPrincipal? {
        return redisTemplate.opsForHash<String, UserDetailsPrincipal>().get(AUTH_REDIS_KEY, token)
    }


    fun updateAuthByToken (token: String, auth: UserDetailsPrincipal){

    }




    /**
     * Key: username
     * Value: Token
     */
    fun setSecUser (username:String, token: String, tokenExpire: Long) {
        redisTemplate.opsForHash<String,String>().put(SECUSER_REDIS_KEY, username, token)
        redisTemplate.expire(username, tokenExpire, TimeUnit.MILLISECONDS)
    }


    fun getTokenByUserName (username: String) : String {
        return redisTemplate.opsForHash<String, String>().get(SECUSER_REDIS_KEY, username)!!
    }

    fun removeSecUser (username: String){
        redisTemplate.opsForHash<String, String>().delete(SECUSER_REDIS_KEY, username)
    }
}