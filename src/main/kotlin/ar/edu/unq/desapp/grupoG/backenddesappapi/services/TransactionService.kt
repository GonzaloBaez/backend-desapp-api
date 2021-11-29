package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.BadRequest
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TransactionService {

    @Autowired
    lateinit var transactionRepository : TransactionRepository

    @Transactional
    fun save(transaction: Transaction) : Transaction{
        return transactionRepository.save(transaction)
    }

    fun findAll() : MutableIterable<Transaction>{
        return transactionRepository.findAll()
    }

    fun findByState(state:String):List<Transaction> {

        val optional : Optional<List<Transaction>> = transactionRepository.findByStateContaining(state)

        return optional.get()
    }

    fun findByCounterPartUser(userEmail:String): List<Transaction> {
        val optional:Optional<List<Transaction>> = transactionRepository.findByCounterPartUserContaining(userEmail)
        return optional.get()
    }

    @Transactional
    fun updateActivityToInProgress(id:Long){
        transactionRepository.updateActivityToInProgress(id)
    }

    @Transactional
    fun setCounterPartUser(transaction: Transaction,counterPartUser: String){
        if(transaction.counterPartUser == null) {
            transaction.counterPartUser = counterPartUser
            transactionRepository.save(transaction)
        } else{
            throw BadRequest("Transaction with id ${transaction.id} is in progress or already closed")
        }
    }

    @Transactional
    fun cancelActivity(id: Long) {
        transactionRepository.cancelActivity(id)
    }

    fun findById(id:Long) : Transaction{
        val optional : Optional<Transaction> = transactionRepository.findById(id)

        if(!optional.isPresent){
            throw NotFoundException("Transaction with id $id doesn't exist")
        }
        return optional.get()
    }

    fun clearDatabase(){
        transactionRepository.deleteAllInBatch()
    }

    @Transactional
    fun deleteTransaction(transaction: Transaction) {
        try{
            transactionRepository.delete(transaction)
        } catch (exception:IllegalArgumentException){
            throw BadRequest(exception.message)
        }
    }
}