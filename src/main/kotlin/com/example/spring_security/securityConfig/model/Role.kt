package com.example.spring_security.securityConfig.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(unique = true)
        var roleName: String? = null,

        var description: String? = null,


        @JsonIgnore
        @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var userRole: MutableList<UserRole>? = null


/*
        @JsonIgnore
        @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        private val roleMenus: List<RoleMenu>? = null,

        @JsonIgnore
        @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var rolePermission: MutableList<RolePermission>? = null
*/

)