package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.BadRequest
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateUniqueException
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.Throws

@Service
class UserService {

    @Autowired
    private lateinit var  repository: UserRepository

    @Transactional(rollbackFor = [DuplicateUniqueException::class])
    @Throws(DuplicateUniqueException::class)
    fun save(user: User): User{
        return try{
            repository!!.save(user)
        } catch (e: DataIntegrityViolationException){
            throw DuplicateUniqueException("Wallet ${user.wallet} or Email ${user.email} already exist")
        }
    }

    @Transactional
    @Throws(BadRequest::class)
    fun closeActivity(transaction: Transaction,counterPartUserEmail:String){
        if(counterPartUserEmail == transaction.counterPartUser) {
            var counterPartUser : User = repository.findByEmail(counterPartUserEmail).get()
            transaction.state = "Cerrada"
            var points = transaction.getPointsForUsers()
            transaction.user.sumPoints(points)
            counterPartUser.sumPoints(points)
            repository.saveAll(listOf(transaction.user,counterPartUser))
        }else{
            throw BadRequest("Invalid information to close transaction")
        }
    }

    @Transactional
    fun getActivitiesFromUser(user:String) : MutableList<Transaction>{
        var savedUser = repository.findByEmail(user).get()
        var activities = savedUser.transactions

        return activities
    }

    @Transactional
    fun addTransactionTo(user:User,transaction: Transaction) : User{
        user.addTransaction(transaction)
        transaction.user = user
        return repository.save(user)
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
            throw NotFoundException("User with email $email doesn't exist")
        }
        return optional.get()
    }

    @Transactional
    fun discountToCancelingUser(cancelingUserEmail: String) {
        var cancelingUser = findByEmail(cancelingUserEmail)
        cancelingUser.discountPoints()
        repository.save(cancelingUser)
    }
}