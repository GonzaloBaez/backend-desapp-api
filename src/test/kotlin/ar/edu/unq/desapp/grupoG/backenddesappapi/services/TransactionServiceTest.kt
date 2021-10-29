package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class TransactionServiceTest {
    private lateinit var transactionBuilder : TransactionBuilder

    @Mock
    lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    lateinit var transactionService : TransactionService

    @Before
    fun setUp(){
        this.transactionBuilder = TransactionBuilder()
    }

    @Test
    fun saveATransactionAndGettingIt(){
        var transactionToSave = transactionBuilder.build()

        Mockito.`when`(transactionRepository.save(transactionToSave)).thenReturn(transactionToSave)
        Mockito.`when`(transactionRepository.findById(transactionToSave.id)).thenReturn(Optional.of(transactionToSave))

        var transactionSaved = transactionService.save(transactionToSave)
        var gettingTransaction = transactionService.findById(transactionSaved.id)

        assertEquals(transactionSaved,gettingTransaction)
    }

    @Test
    fun gettingAllTransactions(){
        var transactionOne = transactionBuilder.build()
        var transactionTwo = transactionBuilder.build()

        Mockito.`when`(transactionRepository.save(transactionOne)).thenReturn(transactionOne)
        Mockito.`when`(transactionRepository.save(transactionTwo)).thenReturn(transactionTwo)
        Mockito.`when`(transactionRepository.findAll()).thenReturn(listOf(transactionOne,transactionTwo))

        var savedTransactionOne = transactionService.save(transactionOne)
        var savedTransactionTwo = transactionService.save(transactionTwo)

        var allTransactions = listOf(savedTransactionOne,savedTransactionTwo)

        var gettingAllTransactions = transactionService.findAll().toMutableList()

        assertEquals(allTransactions,gettingAllTransactions)
    }
}