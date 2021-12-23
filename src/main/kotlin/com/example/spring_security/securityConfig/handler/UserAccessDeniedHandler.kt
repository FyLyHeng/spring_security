package com.example.spring_security.securityConfig.handler

import com.example.spring_security.securityConfig.jwtConfig.model.JwtResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@Component
//class UserAccessDeniedHandler : AccessDeniedHandler {
//
//    @Throws(IOException::class, ServletException::class)
//    override fun handle(p0: HttpServletRequest, p1: HttpServletResponse, p2: AccessDeniedException) {
//        JwtResponse(p2.toString(), Date())
//    }
//}