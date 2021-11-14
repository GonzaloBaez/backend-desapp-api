package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.InvalidUserInformation
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
        this.checkUser(user)
        user.password = passwordEncoder.encode(user.password)
        var savedUser = userService.save(user)
        val responseHeader = HttpHeaders()
        responseHeader.set("location","/api/users" + "/" + user.id)
        return ResponseEntity(savedUser, HttpStatus.CREATED)
    }

    fun checkUser(user:User) {
        require(user.name.length in 3..29){throw InvalidUserInformation("Invalid name, the length must be greater than 2 and less than 30")}
        require(user.surname.length in 3..29){throw InvalidUserInformation("Invalid surname, the length must be greater than 2 and less than 30")}
        requireEmail(user.email)
        require(user.address.length in 10..29){throw InvalidUserInformation("Address invalid length, it should be greater than 9 and less than 30")}
        require(user.cvu.length == 22){throw InvalidUserInformation("Invalid cvu length, it should be 22 digits")}
        require(user.wallet.length == 8){throw InvalidUserInformation("Invalid wallet length, it should be 8 digits")}
    }

    private fun requireEmail(email:String) {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        require(emailRegex.toRegex().matches(email)){
            throw InvalidUserInformation("Invalid email, use format example@zzz.zzz")
        }
    }
}