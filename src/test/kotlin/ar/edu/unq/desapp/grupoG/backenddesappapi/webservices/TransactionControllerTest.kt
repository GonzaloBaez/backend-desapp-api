package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.TransactionService
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.springframework.http.HttpStatus


@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class TransactionControllerTest {

    lateinit var transactionBuilder : TransactionBuilder

    @Mock
    lateinit var transactionService: TransactionService

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var transactionDTO : TransactionDTO

    @Mock
    lateinit var transactionDTOTwo : TransactionDTO

    @InjectMocks
    lateinit var transactionController: TransactionController

    @Before
    fun setUp(){
        transactionBuilder = TransactionBuilder()
    }

    @Test
    fun createATransactionAndGettingIt(){
        var transaction = transactionBuilder.build()

        Mockito.`when`(transactionDTO.user).thenReturn(transaction.user.email)
        Mockito.`when`(transactionDTO.toModel(transaction.user)).thenReturn(transaction)
        Mockito.`when`(userService.findByEmail(transactionDTO.user)).thenReturn(transaction.user)
        Mockito.`when`(transactionService.save(transaction)).thenReturn(transaction)
        Mockito.`when`(transactionService.findAll()).thenReturn(mutableListOf(transaction))

        var savedTransactionDTO = transactionController.create(transactionDTO)
        var gettingTransactionDTO = transactionController.allTransactions()

        assertEquals(savedTransactionDTO.body, gettingTransactionDTO[0])
        assertEquals(savedTransactionDTO.statusCode,HttpStatus.CREATED)
    }

    @Test
    fun allTransactions(){
        var transactionOne = transactionBuilder.build()
        var transactionTwo = transactionBuilder.withUser(UserBuilder().withEmail("z2@gmail.com").build()).build()

        Mockito.`when`(transactionDTO.user).thenReturn(transactionOne.user.email)
        Mockito.`when`(transactionDTOTwo.user).thenReturn(transactionTwo.user.email)
        Mockito.`when`(userService.findByEmail(transactionDTO.user)).thenReturn(transactionOne.user)
        Mockito.`when`(userService.findByEmail(transactionDTOTwo.user)).thenReturn(transactionTwo.user)
        Mockito.`when`(transactionDTO.toModel(transactionOne.user)).thenReturn(transactionOne)
        Mockito.`when`(transactionDTOTwo.toModel(transactionTwo.user)).thenReturn(transactionTwo)
        Mockito.`when`(transactionService.save(transactionOne)).thenReturn(transactionOne)
        Mockito.`when`(transactionService.save(transactionTwo)).thenReturn(transactionTwo)
        Mockito.`when`(transactionService.findAll()).thenReturn(mutableListOf(transactionOne,transactionTwo))

        var createdTrOne = transactionController.create(transactionDTO).body
        var createdTrTwo = transactionController.create(transactionDTOTwo).body

        var allTransactions = mutableListOf(createdTrOne,createdTrTwo)
        var gettingAllTrs = transactionController.allTransactions()

        assertEquals(allTransactions,gettingAllTrs)
    }


}