package com.example.spring_security.simpleResponce

import com.example.spring_security.simpleResponce.ResponseObject
import kotlin.collections.HashMap

class ResponseObjectMap {
    val responseObject = ResponseObject()

    fun responseObject(obj: Any?, currentPage: Int, perPage: Int, totalElements: Long): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["results"] = obj
            response["currentPage"] = currentPage
            response["perPage"] = perPage
            response["length"] = totalElements
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseObject(obj: Any?, totalElements: Long): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["results"] = obj
            response["length"] = totalElements
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseObject(obj: Any?): MutableMap<String, Any> {
        val response: MutableMap<String, Any> = HashMap()
        if (obj != null) {
            response["results"] = obj
            response["response"] = responseObject.success()
        } else {
            response["response"] = responseObject.error()
        }
        return response
    }

    fun responseCodeWithMessage(code: Int?, message:String): MutableMap<String, Any> {
        val responseObject = ResponseObject(code = code, message = message)
        val response: MutableMap<String, Any> = HashMap()
        response["response"] = responseObject
        return response
    }
}

