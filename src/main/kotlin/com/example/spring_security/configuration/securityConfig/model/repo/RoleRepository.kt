package com.example.spring_security.configuration.securityConfig.model.repo

import com.example.spring_security.configuration.securityConfig.model.Role
import com.example.spring_security.configuration.securityConfig.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository :JpaRepository<Role, Long>{

    fun findByRoleName(roleName: String) :Role?

    fun findAllByUserRoleIn(userRoles : List<UserRole>) : MutableList<Role>

}
