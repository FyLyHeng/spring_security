package com.example.spring_security.configuration.securityConfig.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
class RequestMapping(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(nullable = false)
        var httpMethod: String = "All",

        @Column(nullable = false)
        var configAttribute: String = "",

        @Column(nullable = false, unique = true)
        var url: String = "",

        var isPermitAll : Boolean = false,


        /**
         * These tow field use form convert string list to List.
         *
         * Fields not store any value in DB
         * Fields not show in Json
         */
        @Transient
        @JsonIgnore
        var configAttributes: List<String>? = listOf(),

        @Transient
        @JsonIgnore
        var httpMethods: MutableList<String>? = mutableListOf(),

        @Transient
        @JsonIgnore
        var urls: MutableList<String>? = mutableListOf()

)
