package com.example.spring_security.securityConfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {


//    @Autowired
//    lateinit var dataSource : DataSource

    @Qualifier("userDetailServiceImp")
    @Autowired
    lateinit var userDetailsService: UserDetailsService


    /**
     * Authentication
     *
     * Verify the user & role login (who is request to login)
     */

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {


        //Load user by In memory
       /* auth.inMemoryAuthentication()
                .withUser("liza").password("liza").roles("USER").and()
                .withUser("admin").password("pass").roles("ADMIN")
        */


        //Load User by JDBC
        /* auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username =?")
        */


        //Load User by JPA implement
        auth.userDetailsService(userDetailsService)

    }




    /**
     * Authorization
     *
     * Verify the url and http method(get, put, post)
     * config the level of allow to access the APIs
     */

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")

                .and().formLogin()
    }






    /**
     * this Fun need to exist.
     */

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return NoOpPasswordEncoder.getInstance()
    }


}