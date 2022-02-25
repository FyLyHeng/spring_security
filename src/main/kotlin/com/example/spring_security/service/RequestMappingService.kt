package com.example.spring_security.service

import com.example.spring_security.configuration.securityConfig.model.repo.RequestMappingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RequestMappingService {

    @Autowired
    lateinit var requestMappingRepository: RequestMappingRepository
    var REQUEST_MAPPING_PERMITALL  =  mutableListOf<String>()
    var REQUEST_MAPPING = mutableMapOf<String, List<String>>()



    fun loadRequestMap() {

        val requestMapPermitAll = mutableListOf<String>()
        val requestMapAuth = mutableMapOf<String, List<String>>()

        val rs = requestMappingRepository.findAll()
        rs.forEach { it ->

            //Add urls that permitAll into val
            if (it.isPermitAll){
                requestMapPermitAll.add(it.url.trim())
            }
            else{

                it.configAttributes = it.configAttribute.split(",").map { it.trim()}

                requestMapAuth.put(it.url , it.configAttributes!!)
            }
            println("LOAD requestMap:  ${it.url} : ${it.configAttribute}")
        }



        this.REQUEST_MAPPING_PERMITALL = requestMapPermitAll
        this.REQUEST_MAPPING = requestMapAuth
    }

}
