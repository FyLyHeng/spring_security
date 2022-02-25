package com.example.spring_security.configuration.securityConfig.model.repo

import com.example.spring_security.configuration.securityConfig.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {

//    fun findByUsers(username: User): UserRole
//    fun findByRole(username: Role): UserRole
}
