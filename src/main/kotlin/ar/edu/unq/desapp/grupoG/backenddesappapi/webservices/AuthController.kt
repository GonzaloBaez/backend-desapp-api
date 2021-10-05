package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableAutoConfiguration
class AuthController {
    @Autowired
    lateinit var userService : UserService

    @Autowired
    lateinit var passwordEncoder : PasswordEncoder

    @CrossOrigin(origins = ["http://localhost:3000"])
    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            user.password = passwordEncoder.encode(user.password)
            userService.save(user)
            val responseHeader = HttpHeaders()
            responseHeader.set("location","/api/users" + "/" + user.id)
            ResponseEntity(responseHeader, HttpStatus.CREATED)
        }catch (e: DataIntegrityViolationException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}