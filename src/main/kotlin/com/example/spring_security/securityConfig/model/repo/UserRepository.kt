package com.example.spring_security.securityConfig.model.repo

import com.example.spring_security.securityConfig.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {

    fun findByUsername(username: String): Users?
}