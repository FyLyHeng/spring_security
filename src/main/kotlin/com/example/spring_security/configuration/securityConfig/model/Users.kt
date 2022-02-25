package com.example.spring_security.configuration.securityConfig.model

import com.example.spring_security.configuration.securityConfig.UserDetailsPrincipal
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@JsonIgnoreProperties(value = ["password"], allowSetters = true)
@Table(indexes = [Index(columnList = "username"),Index(columnList = "email")])
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
