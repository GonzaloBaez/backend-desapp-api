package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserControllerTest {

    var userBuilder = UserBuilder()

    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var userController: UserController

    @Test
    fun gettingAllUsers(){
        val userOne = userBuilder.build()
        val userTwo = userBuilder.withEmail("z2@gmail.com").build()

        val createdUsers = mutableListOf(userOne.toDTO(),userTwo.toDTO())

        Mockito.`when`(userService.findAll()).thenReturn(mutableListOf(userOne,userTwo))

        val allUsers = userController.allUsers()

        assertEquals(createdUsers,allUsers)
    }
}