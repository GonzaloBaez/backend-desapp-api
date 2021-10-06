package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
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
        val userTwo = userBuilder.build()

        val createdUsers = mutableListOf(userOne,userTwo)

        Mockito.`when`(userService.findAll()).thenReturn(mutableListOf(userOne,userTwo))

        val allUsers = userController.allUsers()

        assertThat(createdUsers == allUsers)
    }
}