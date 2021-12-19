package com.example.spring_security

import com.example.spring_security.securityConfig.model.Role
import com.example.spring_security.securityConfig.model.UserRole
import com.example.spring_security.securityConfig.model.Users
import com.example.spring_security.securityConfig.model.repo.RoleRepository
import com.example.spring_security.securityConfig.model.repo.UserRepository
import com.example.spring_security.securityConfig.model.repo.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class BootStrap : CommandLineRunner {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    override fun run(vararg args: String?) {

        upUser()
    }






    private fun upUser (){
        initDefaultUsers()
        initDefaultRole()
        initDefaultUserLogin()
    }





    private fun initDefaultUserLogin (){
        userRepository.findAll().forEach {
            if (it.username == "admin"){
                userRoleRepository.save(UserRole(users = it, role = roleRepository.findByRoleName("ROLE_ADMIN")))
            }

            if (it.username == "user"){
                userRoleRepository.save(UserRole(users = it, role = roleRepository.findByRoleName("ROLE_USER")))
            }
        }
    }
    private fun initDefaultRole(): MutableList<Role> {
        val rs = mutableListOf<Role>()

        val listDefaultRole = listOf("ROLE_ADMIN","ROLE_USER")

        listDefaultRole.forEach {
            if (roleRepository.findByRoleName(it)== null ){
                rs.add(roleRepository.save(Role(roleName = it,description = "default role.")))
            }
        }

        return rs
    }
    private fun initDefaultUsers(): MutableList<Users> {
        val rs = mutableListOf<Users>()

        val listDefaultUser = listOf("admin","user")

        listDefaultUser.forEach {
            if (userRepository.findByUsername(it) == null){
                rs.add(userRepository.save(Users(username = it,email = "$it@mai.com",password = it, phone = "012345678")))
            }
        }

        return rs
    }
}