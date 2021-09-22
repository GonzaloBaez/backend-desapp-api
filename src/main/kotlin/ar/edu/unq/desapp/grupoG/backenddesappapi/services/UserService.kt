package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {

    @Autowired
    private lateinit var  repository: UserRepository

    @Transactional
    fun save(user: User): User{
        return repository!!.save(user)
    }

    fun findById(id:Long):User{
        return repository!!.findById(id).get()
    }

    fun findAll(): MutableIterable<User> {
        return repository!!.findAll()
    }
}