package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
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

    @Transactional
    fun updateActivityToInProgress(id:Long){
        transactionRepository.updateActivityToInProgress(id)
    }
}