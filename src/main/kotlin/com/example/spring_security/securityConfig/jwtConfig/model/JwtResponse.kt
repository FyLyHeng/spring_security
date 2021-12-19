package com.example.spring_security.securityConfig.jwtConfig.model

import java.util.*

class JwtResponse (
    var token: String,
    var expireIn: Date
)