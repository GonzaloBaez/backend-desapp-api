package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun findById(id: Long):Transaction{
        var transaction = transactionRepository.findById(id)

        if(!transaction.isPresent){
            throw NotFoundException("Transaction with id $id doesn't exist")
        }
        return transaction.get()
    }
}