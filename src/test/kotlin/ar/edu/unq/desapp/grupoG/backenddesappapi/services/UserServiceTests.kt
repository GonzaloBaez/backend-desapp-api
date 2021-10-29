package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.After
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class UserServiceTests {

	private lateinit var userBuilder : UserBuilder

	@Mock
	lateinit var userRepository: UserRepository

	@InjectMocks
	lateinit var userService : UserService

	@Before
	fun setUp(){
		this.userBuilder = UserBuilder()
	}

	@Test
	fun savingAUserAndGettingItById() {
		var userToRegister:User = userBuilder.build()

		Mockito.`when`(userRepository.save(userToRegister)).thenReturn(userToRegister)
		Mockito.`when`(userRepository.findById(userToRegister.id)).thenReturn(Optional.of(userToRegister))

		val userRegistered = userService.save(userToRegister)
		var obtainedUser :User= userService.findById(userToRegister.id)

		assertEquals(userRegistered,obtainedUser)
	}

	@Test
	fun savingAUserAndGettingItByEmail(){
		var userToRegister = userBuilder.build()

		Mockito.`when`(userRepository.save(userToRegister)).thenReturn(userToRegister)
		Mockito.`when`(userRepository.findByEmail(userToRegister.email)).thenReturn(Optional.of(userToRegister))

		val userRegistered = userService.save(userToRegister)
		val savedUser = userService.findByEmail(userToRegister.email)

		assertEquals(userRegistered,savedUser)
	}

	@Test
	fun `gettingShouldFailIfIdDoesn'tExist`(){
		assertThatThrownBy { userService.findById(100) }.isInstanceOf(NotFoundException::class.java)
			.withFailMessage("User with id 100 doesn't exist")
	}

	@Test
	fun `gettingShouldFailIfEmailDoesn'tExist`(){
		assertThatThrownBy { userService.findByEmail("100@gmail.com") }.isInstanceOf(NotFoundException::class.java)
			.withFailMessage("User with email 100@gmail.com doesn't exist")
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameWallet(){
		var userOne : User = userBuilder.withWallet("12345678").build()
		var userTwo : User = userBuilder.withWallet("12345678").build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)
		userService.save(userOne)
		Mockito.`when`(userRepository.save(userTwo)).thenThrow(DataIntegrityViolationException::class.java)

		assertThatThrownBy { userService.save(userTwo) }.isInstanceOf(DataIntegrityViolationException::class.java)
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameEmail(){
		var userOne : User = userBuilder.withEmail("userOne@gmail.com").build()
		var userTwo : User = userBuilder.withEmail("userOne@gmail.com").build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)
		userService.save(userOne)
		Mockito.`when`(userRepository.save(userTwo)).thenThrow(DataIntegrityViolationException::class.java)

		assertThatThrownBy { userService.save(userTwo) }.isInstanceOf(DataIntegrityViolationException::class.java)
	}

	@Test
	fun gettingAllUsers(){
		var userOne = userBuilder.withWallet("12345678").withEmail("userOne@asd").build()
		var userTwo = userBuilder.withWallet("04040400").withEmail("userTwo@asd").build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)
		Mockito.`when`(userRepository.save(userTwo)).thenReturn(userTwo)
		Mockito.`when`(userRepository.findAll()).thenReturn(listOf(userOne,userTwo))

		var listOfUser = listOf(userService.save(userOne),userService.save(userTwo))
		var allSavedUsers = userService.findAll().toMutableList()

		assertEquals(allSavedUsers.size,2)
		assertEquals(listOfUser,allSavedUsers)
	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
