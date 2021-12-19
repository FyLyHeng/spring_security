package com.example.spring_security.securityConfig.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
data class UserRole(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = 0,


        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "users_id")
        @JsonIgnore
        var users: Users? = null,


        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "role_id")
        @JsonIgnore
        var role: Role? = null
)