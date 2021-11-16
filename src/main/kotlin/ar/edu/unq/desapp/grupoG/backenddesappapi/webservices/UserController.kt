package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.aspects.LogAudit
import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.UserDTO
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

    @LogAudit
    @GetMapping
    fun allUsers():List<UserDTO>{
        return userService.findAll().map { it.toDTO() }
    }
}