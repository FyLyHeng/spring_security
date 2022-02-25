package com.example.spring_security.configuration.RedisConfig

import com.example.spring_security.configuration.securityConfig.UserDetailsPrincipal
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
//        redisTemplate.opsForHash<String,String>().delete(AUTH_REDIS_KEY,token)
        val tokenKey = "$AUTH_REDIS_KEY:$token"
        redisTemplate.delete(tokenKey)
    }


    fun setAuth (token: String, auth: UserDetailsPrincipal, tokenExpire: Long) {
        /*
        redisTemplate.opsForHash<String, UserDetailsPrincipal>().put(AUTH_REDIS_KEY, token, auth)
        redisTemplate.expire(token, tokenExpire, TimeUnit.MILLISECONDS)
        */

        val tokenKey = "$AUTH_REDIS_KEY:$token"
        redisTemplate.opsForValue().set(tokenKey, auth)
        redisTemplate.expire(tokenKey, tokenExpire, TimeUnit.MILLISECONDS)

    }



    fun getAuth(token: String): UserDetailsPrincipal? {
        //return redisTemplate.opsForHash<String, UserDetailsPrincipal>().get(AUTH_REDIS_KEY, token)
        val tokenKey = "$AUTH_REDIS_KEY:$token"
        return redisTemplate.opsForValue().get(tokenKey) as UserDetailsPrincipal
    }


    fun updateAuthByToken (token: String, auth: UserDetailsPrincipal){
        val tokenKey = "$AUTH_REDIS_KEY:$token"
        val expDate = redisTemplate.getExpire(tokenKey)
        val currentAuth = redisTemplate.opsForValue().get(tokenKey)

        if (expDate < 0 && currentAuth == null){
            throw Exception("Token broken")
        }
        redisTemplate.opsForValue().set(tokenKey,auth, expDate)
    }




    /**
     * Key: username
     * Value: Token
     */
    fun addTokenToSecUser (username:String, token: String, tokenExpire: Long) {
        redisTemplate.opsForHash<String,String>().put(SECUSER_REDIS_KEY, username, token)
        //redisTemplate.expire(username, tokenExpire, TimeUnit.MILLISECONDS)
    }

    fun getTokenByUserName (username: String) : String? {
        return redisTemplate.opsForHash<String, String>().get(SECUSER_REDIS_KEY, username)
    }

    fun removeTokenFromSecUser (username: String){
        redisTemplate.opsForHash<String, String>().delete(SECUSER_REDIS_KEY, username)
    }
}
