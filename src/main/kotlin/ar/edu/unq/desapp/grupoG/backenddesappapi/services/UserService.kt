package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService {

    @Autowired
    private lateinit var  repository: UserRepository

    @Transactional
    fun save(user: User): User{
        return repository!!.save(user)
    }

    fun findById(id:Long):User{
        val optional : Optional<User> = repository.findById(id)

        if (!optional.isPresent){
            throw NotFoundException("User with id $id doesn't exist")
        }
        return optional.get()
    }

    fun findAll(): MutableIterable<User> {
        return repository!!.findAll()
    }

    fun clearDatabase(){
        repository.deleteAllInBatch()
    }

    fun findByEmail(email: String) : User{
        val optional : Optional<User> = repository.findByEmail(email)

        if(!optional.isPresent){
            throw NotFoundException("User not found with email $email")
        }
        return optional.get()
    }
}