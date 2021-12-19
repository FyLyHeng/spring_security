package com.example.spring_security.securityConfig

import com.example.spring_security.securityConfig.model.UserRole
import com.example.spring_security.securityConfig.model.repo.RoleRepository
import com.example.spring_security.securityConfig.model.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImp : UserDetailsService{

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository



    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username)?:throw UsernameNotFoundException ("User Not Found")
        val authority = this.getAuthorities(user.userRole!!)


        return UserDetailsPrincipal.create(user.id!!,user.username,user.email, user.password, authority)
    }




    fun getAuthorities(userRoleIds: List<UserRole>): MutableList<SimpleGrantedAuthority> {

        val authorities = mutableListOf<SimpleGrantedAuthority>()

        roleRepository.findAllByUserRoleIn(userRoleIds).forEach {
            authorities.add(SimpleGrantedAuthority(it.roleName))
        }

        return authorities

/*        return mutableListOf(
                SimpleGrantedAuthority("ROLE_USER")
        )*/
    }

}