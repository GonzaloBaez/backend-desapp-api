package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateUniqueException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/users")
@CrossOrigin
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
            var savedUser = userService.save(user)
            val responseHeader = HttpHeaders()
            responseHeader.set("location","/api/users" + "/" + user.id)
            ResponseEntity(savedUser, HttpStatus.CREATED)
        }catch (e: DuplicateUniqueException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}