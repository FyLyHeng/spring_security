package com.example.spring_security.securityConfig.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Users (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(unique = true)
        var username: String,

        @Column(unique = true)
        var email: String,

        var phone: String,
        var password: String,
        var isActive: Boolean = true,

        @JsonIgnore
        @OneToMany(mappedBy = "users",fetch = FetchType.EAGER)
        var userRole: MutableList<UserRole>?=null
)