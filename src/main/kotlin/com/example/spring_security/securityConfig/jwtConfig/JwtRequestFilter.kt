package com.example.spring_security.securityConfig.jwtConfig

import com.example.spring_security.securityConfig.UserDetailServiceImp
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtUtils: JwtUtils
    @Autowired
    lateinit var userDetailsService: UserDetailServiceImp





    /**
     * Same contract as for `doFilter`, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See [.shouldNotFilterAsyncDispatch] for details.
     *
     * Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            val jwt = authorizationHeader.substring(7)


            //check is token exist in redis
            val auth = userDetailsService.loadUserByUserNameRedis(jwt)


            //validate user from value (redis) with username from extract
            jwtUtils.validateToken(jwt, auth)


            //return userDetail to UsernamePasswordAuthenticationToken
            if (SecurityContextHolder.getContext().authentication == null) {
                val authenticationToken = UsernamePasswordAuthenticationToken(auth, null, auth.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }

    /*@Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwt :String?=null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7)

            try {
                username = jwtUtils.extractUsername(jwt)

            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println(e.message)

                throw ExpiredJwtException(null,null,e.message)
            }
        }


        if (username != null && SecurityContextHolder.getContext().authentication == null) {

            println("by filter")
            val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(username)

            if (jwtUtils.validateToken(jwt!!, userDetails)!!) {

                //Todo
                val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        filterChain.doFilter(request,response)
    }*/
}