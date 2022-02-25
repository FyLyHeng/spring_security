package com.example.spring_security.configuration.auditing

import com.example.spring_security.configuration.securityConfig.UserDetailsPrincipal
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


class SpringSecurityAuditorAware : AuditorAware<Long> {

    override fun getCurrentAuditor(): Optional<Long> {

        try {
            val auth: UserDetailsPrincipal = SecurityContextHolder.getContext().authentication?.principal as UserDetailsPrincipal
            return Optional.of(auth.id)

        }catch (e:Exception){
            println(e.message)
        }
        return Optional.empty()
    }
}
