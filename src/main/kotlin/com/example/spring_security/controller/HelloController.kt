package com.example.spring_security.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloController {


    @GetMapping
    fun home(): String {

        return "Welcome home"
    }

    @GetMapping("/admin")
    fun admin(): String {

        return "Welcome Admin"
    }

    @GetMapping("/user")
    fun user(): String {

        return "Welcome User"
    }
}