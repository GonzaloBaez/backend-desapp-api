package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/users")
@CrossOrigin
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun allUsers():MutableIterable<User>{
        return userService.findAll()
    }
}