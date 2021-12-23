package com.example.spring_security.securityConfig.handler

import org.springframework.security.core.userdetails.UsernameNotFoundException


class UserAuthNotExist(msg:String) : UsernameNotFoundException(msg){

    override val message: String?
        get() = "ERROR NOT WORKING"
}