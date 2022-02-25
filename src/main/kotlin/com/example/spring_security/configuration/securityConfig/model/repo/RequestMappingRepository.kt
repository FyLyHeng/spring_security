package com.example.spring_security.configuration.securityConfig.model.repo

import com.example.spring_security.configuration.securityConfig.model.RequestMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestMappingRepository :JpaRepository<RequestMapping, Long>{

    fun findByUrl(url:String):RequestMapping?

    fun findAllByIsPermitAllFalse():MutableList<RequestMapping>

}
