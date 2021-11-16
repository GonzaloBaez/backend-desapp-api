package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.TransactionService
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.http.HttpStatus


@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class TransactionControllerTest {

    lateinit var transactionBuilder : TransactionBuilder

    lateinit var userBuilder: UserBuilder

    @Mock
    lateinit var transactionService: TransactionService

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var transactionDTO : TransactionDTO

    @InjectMocks
    lateinit var transactionController: TransactionController

    @Before
    fun setUp(){
        transactionBuilder = TransactionBuilder()
        userBuilder = UserBuilder()
    }

    @Test
    fun createATransactionAndGettingIt(){
        var transaction = transactionBuilder.build()

        Mockito.`when`(transactionDTO.user).thenReturn(transaction.user.email)
        Mockito.`when`(transactionDTO.toModel(transaction.user)).thenReturn(transaction)
        Mockito.`when`(userService.findByEmail(transactionDTO.user)).thenReturn(transaction.user)
        Mockito.`when`(transactionService.findAll()).thenReturn(mutableListOf(transaction))
        Mockito.`when`(userService.addTransactionTo(transaction.user,transaction)).then {
            transaction.user.addTransaction(transaction)
            transaction.user}

        var savedTransactionDTO = transactionController.create(transactionDTO)
        var gettingTransactionDTO = transactionController.allTransactions()

        assertEquals(savedTransactionDTO.body, gettingTransactionDTO[0])
        assertEquals(HttpStatus.CREATED,savedTransactionDTO.statusCode)
    }

    @Test
    fun allTransactions(){
        var transactionOne = transactionBuilder.build()
        var transactionTwo = transactionBuilder.withUser(UserBuilder().withEmail("z2@gmail.com").build()).build()

        Mockito.`when`(transactionService.findAll()).thenReturn(mutableListOf(transactionOne,transactionTwo))

        var expectedTransactions = mutableListOf(transactionOne.toDTO(),transactionTwo.toDTO())
        var allTransactions = transactionController.allTransactions()

        assertEquals(expectedTransactions,allTransactions)
    }

    @Test
    fun gettingCreatedActivities(){
        var transactionOne = transactionBuilder.withUser(userBuilder.withEmail("z4@gmail.com").build()).build()
        var transactionTwo = transactionBuilder.withUser(userBuilder.withEmail("z3@gmail.com").withWallet("34434567").build()).build()

        Mockito.`when`(transactionService.findByState("Creada")).thenReturn(mutableListOf(transactionOne,transactionTwo))

        var allActivities = transactionController.allActivities()
        var expectedActivities = mutableListOf(transactionOne.toDTO(),transactionTwo.toDTO())

        assertEquals(expectedActivities,allActivities)
    }

    @Test
    fun updateATransactionToInProgressState(){
        var transaction = transactionBuilder.build()
        var user = userBuilder.withEmail("z2@gmail.com").build()

        Mockito.`when`(transactionService.findById(transaction.id)).thenReturn(transaction)
        Mockito.`when`(userService.findByEmail("z2@gmail.com")).thenReturn(user)

        transactionController.updateActivityToInProgress(transaction.id,"z2@gmail.com")

        Mockito.verify(transactionService, times(1)).updateActivityToInProgress(transaction.id)
        Mockito.verify(transactionService,times(1)).setCounterPartUser(transaction,"z2@gmail.com")
    }

    @Test
    fun getActivitiesFromCounterPartUser(){
        var userOne = userBuilder.withEmail("z1@gmail.com").build()
        var transactionOne = transactionBuilder.withUser(userOne).build()
        var transactionTwo = transactionBuilder.withUser(userOne).build()
        var userCounterPart = userBuilder.withEmail("z2@gmail.com").build()

        Mockito.`when`(transactionService.findByCounterPartUser("z2@gmail.com")).thenReturn(mutableListOf(transactionOne,transactionTwo))

        var transactions = transactionController.activitiesFromCounterPartUser(userCounterPart.email)
        var expectedTransactions = mutableListOf(transactionOne.toDTO(),transactionTwo.toDTO())

        assertEquals(expectedTransactions,transactions)
    }

    @Test
    fun cancelActivity(){
        var transaction = transactionBuilder.build()

        transactionController.cancelActivity(transaction.id,"z@gmail.com")

        Mockito.verify(transactionService, times(1)).cancelActivity(transaction.id)
        Mockito.verify(userService, times(1)).discountToCancelingUser("z@gmail.com")
    }

    @Test
    fun closeActivity(){
        var transaction = transactionBuilder.build()
        var user = userBuilder.withEmail("z2@gmail.com").build()

        Mockito.`when`(userService.findByEmail(user.email)).thenReturn(user)
        Mockito.`when`(transactionService.findById(transaction.id)).thenReturn(transaction)
        Mockito.`when`(transactionService.updateActivityToInProgress(transaction.id)).then {
            transaction.state = "En progreso"
            transaction.counterPartUser = "z2@gmail.com"
            transaction
        }
        transactionController.updateActivityToInProgress(transaction.id,"z2@gmail.com")
        var response = transactionController.closeActivity(transaction.id,"z2@gmail.com")

        Mockito.verify(userService, times(1)).closeActivity(transaction,"z2@gmail.com")
        assertEquals(HttpStatus.OK,response.statusCode)
    }

    @Test
    fun getActivitiesFromUser(){
        var userOne = userBuilder.build()
        var transactionOne = transactionBuilder.withUser(userOne).build()
        var transcationTwo = transactionBuilder.withUser(userOne).build()

        Mockito.`when`(userService.getActivitiesFromUser(userOne.email)).thenReturn(mutableListOf(transactionOne,transcationTwo))

        var response = transactionController.getActivitiesByUser(userOne.email)
        var expectedTransactions = mutableListOf(transactionOne.toDTO(),transcationTwo.toDTO())

        assertEquals(HttpStatus.OK,response.statusCode)
        assertEquals(expectedTransactions,response.body)
    }


}