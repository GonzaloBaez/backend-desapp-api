package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.TransactionRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
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
    fun `gettingShouldFailIfTransactionIdDoesn'tExist`(){
        Mockito.`when`(transactionRepository.findById(100)).thenReturn(Optional.empty())
        var error = assertThrows(NotFoundException::class.java) {
            transactionService.findById(100)
        }

        assertEquals("Transaction with id 100 doesn't exist",error.message)
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

    @Test
    fun updateAnTransactionToInProgressAndGettingIt(){
        var transaction = transactionBuilder.build()
        var transactionTwo = transactionBuilder.build()
        transactionTwo.id = 1

        Mockito.`when`(transactionRepository.updateActivityToInProgress(transaction.id))
            .then { transaction.state = "En progreso"
                    transaction }
        Mockito.`when`(transactionRepository.findByStateContaining("En progreso"))
            .thenReturn(Optional.of(mutableListOf(transaction)))
        Mockito.`when`(transactionRepository.findById(transaction.id)).thenReturn(Optional.of(transaction))

        transactionService.updateActivityToInProgress(transaction.id)
        val gettingTransaction = transactionService.findByState("En progreso")
        val expectingTransaction = mutableListOf(transaction)

        assertEquals(expectingTransaction,gettingTransaction)
        assertEquals("En progreso",transactionService.findById(transaction.id).state)
    }

    @Test
    fun setUserCounterPartToTransaction(){
        var transaction = transactionBuilder.build()

        Mockito.`when`(transactionRepository.findById(transaction.id)).thenReturn(Optional.of(transaction))

        transactionService.setCounterPartUser(transaction.id,"z2@gmail.com")

        Mockito.verify(transactionRepository,times(1)).save(transaction)
        assertEquals(transaction.counterPartUser,"z2@gmail.com")
    }

    @Test
    fun cancelActivity(){
        var transaction = transactionBuilder.build()

        Mockito.`when`(transactionRepository.findById(transaction.id)).thenReturn(Optional.of(transaction))
        Mockito.`when`(transactionRepository.updateActivityToInProgress(transaction.id)).then {
            transaction.state = "En progreso"
            transaction}
        Mockito.`when`(transactionRepository.cancelActivity(transaction.id))
            .then { transaction.state = "Creada"
                transaction.counterPartUser = null
                transaction}
        transactionService.updateActivityToInProgress(transaction.id)
        transactionService.setCounterPartUser(transaction.id,"z2@gmail.com")
        val transactionOldState = transaction.state
        val transactionOldCounterpartUser = transaction.counterPartUser

        transactionService.cancelActivity(transaction.id)

        assertEquals(transaction.counterPartUser,null)
        assertEquals(transaction.state,"Creada")
        assertNotEquals(transaction.state,transactionOldState)
        assertNotEquals(transaction.counterPartUser,transactionOldCounterpartUser)
    }


}