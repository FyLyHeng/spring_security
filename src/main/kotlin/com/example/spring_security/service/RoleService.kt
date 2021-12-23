package com.example.spring_security.service

import com.example.spring_security.securityConfig.model.UserRole
import com.example.spring_security.securityConfig.model.repo.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class RoleService {

    @Autowired
    lateinit var roleRepository: RoleRepository


    fun getAuthorities(userRoleIds: List<UserRole>): MutableList<SimpleGrantedAuthority> {
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        roleRepository.findAllByUserRoleIn(userRoleIds).forEach {
            authorities.add(SimpleGrantedAuthority(it.roleName))
        }
        return authorities
    }
}