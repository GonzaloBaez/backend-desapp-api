package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun allUsers():MutableIterable<User>{
        return userService.findAll()
    }

    @CrossOrigin(origins = ["http://localhost:3000"])
    @PostMapping("/register")
    fun register(@RequestBody user:User):ResponseEntity<Any>{
      return try {
          userService.save(user)
          val responseHeader = HttpHeaders()
          responseHeader.set("location","/api/users" + "/" + user.id)
          ResponseEntity(responseHeader, HttpStatus.CREATED)
      }catch (e:Exception){
          ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
      }
    }
}