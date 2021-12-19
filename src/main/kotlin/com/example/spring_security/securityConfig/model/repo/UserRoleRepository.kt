package com.example.spring_security.securityConfig.model.repo

import com.example.spring_security.securityConfig.model.Role
import com.example.spring_security.securityConfig.model.UserRole
import com.example.spring_security.securityConfig.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {

//    fun findByUsers(username: User): UserRole
//    fun findByRole(username: Role): UserRole
}