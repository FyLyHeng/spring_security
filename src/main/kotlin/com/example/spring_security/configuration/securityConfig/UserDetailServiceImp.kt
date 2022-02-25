package com.example.spring_security.configuration.securityConfig

import com.example.spring_security.configuration.RedisConfig.UserRedisRepository
import com.example.spring_security.configuration.securityConfig.model.UserRole
import com.example.spring_security.configuration.securityConfig.model.repo.RoleRepository
import com.example.spring_security.configuration.securityConfig.model.repo.UserRepository
import com.example.spring_security.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImp : UserDetailsService{

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userRedisRepository: UserRedisRepository
    @Autowired
    lateinit var userService: UserService


    /**
     * Load User credentials
     * Load Form Database
     */
    override fun loadUserByUsername(username: String): UserDetailsPrincipal {

        println("Log:: read User from DB")

//        val user = userRepository.findByUsername(username)?:throw UsernameNotFoundException("User not found with username: $username")
        val user = userService.getUser(username)?:throw UsernameNotFoundException("User not found with username: $username")
        val authority = getAuthorities(user.userRole!!)
        return UserDetailsPrincipal.create(user.id!!,user.username,user.email, user.password, authority)
    }


    @Throws(ExpiredJwtException::class)
    fun loadUserByUserNameRedis(token: String): UserDetailsPrincipal {

        println("Log:: read User from Redis")

        val user = userService.getUserFromRedis(token) ?: throw ExpiredJwtException(null, null, "Token Expired")
        /*val authority = getAuthorities(user.userRole!!)
        return UserDetailsPrincipal.create(user.id!!, user.username, user.email, user.password, authority)*/

        println("READ from REDIS:: ${user.username}")
        println("READ from REDIS:: ${user.auth}")

        return user
    }



    /**
     * Todo get list Role from redis
     */
    private fun getAuthorities(userRoleIds: List<UserRole>): MutableList<SimpleGrantedAuthority> {
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        roleRepository.findAllByUserRoleIn(userRoleIds).forEach {
            authorities.add(SimpleGrantedAuthority(it.roleName))
        }
        return authorities
    }

}
