package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateUniqueException
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder

@RunWith(MockitoJUnitRunner::class)
class AuthControllerTest {

    private var userBuilder : UserBuilder = UserBuilder()

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    lateinit var authController: AuthController

    @Test
    fun registerUser(){
        val newUser = userBuilder.withPassword("a").build()

        Mockito.`when`(passwordEncoder.encode("a")).thenReturn("\$2a\$10$/zNDbzGLuyADbN0Gwf.9rqcLYwfu2L1QrocnAw1VdbhhmNs2Zu")
        Mockito.`when`(userService.save(newUser)).thenReturn(newUser)

        val registeredUser = authController.register(newUser)

        assertEquals(registeredUser.body,newUser)
        assertNotEquals(newUser.password,"a")
        assertEquals(registeredUser.statusCode,HttpStatus.CREATED)
    }

    @Test
    fun registerShouldResponseWithBadRequestIfEmailAlreadyExist(){
        val newUser = userBuilder.withEmail("user@gmail.com").build()
        val newUserTwo = userBuilder.withWallet("123145").withEmail("user@gmail.com").build()

        Mockito.`when`(userService.save(newUser)).thenReturn(newUser)
        Mockito.`when`(passwordEncoder.encode(newUserTwo.password)).thenReturn("\$2a\$10$/zNDbzGLuyADbN0Gwf.9rqcLYwfu2L1QrocnAw1VdbhhmNs2Zu")
        Mockito.`when`(userService.save(newUserTwo)).thenThrow(DuplicateUniqueException::class.java)

        authController.register(newUser)

        var response = authController.register(newUserTwo)

        assertEquals(HttpStatus.BAD_REQUEST,response.statusCode)
    }

    @Test
    fun registerShouldResponseWithBadRequestIfWalletAlreadyExist(){
        val newUser = userBuilder.withWallet("123145").withEmail("user@gmail.com").build()
        val newUserTwo = userBuilder.withWallet("123145").build()

        Mockito.`when`(userService.save(newUser)).thenReturn(newUser)
        Mockito.`when`(passwordEncoder.encode(newUserTwo.password)).thenReturn("\$2a\$10$/zNDbzGLuyADbN0Gwf.9rqcLYwfu2L1QrocnAw1VdbhhmNs2Zu")
        Mockito.`when`(userService.save(newUserTwo)).thenThrow(DuplicateUniqueException::class.java)

        authController.register(newUser)

        var response = authController.register(newUserTwo)

        assertEquals(HttpStatus.BAD_REQUEST,response.statusCode)
    }
}