package com.example.spring_security.securityConfig.model.repo

import com.example.spring_security.securityConfig.model.Role
import com.example.spring_security.securityConfig.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository :JpaRepository<Role, Long>{

    fun findByRoleName(roleName: String) :Role?

    fun findAllByUserRoleIn(userRoles : List<UserRole>) : MutableList<Role>

}