package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateUniqueException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
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

	@Mock
	lateinit var transaciton : Transaction

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
		Mockito.`when`(userRepository.findById(100)).thenReturn(Optional.empty())

		var error = assertThrows(NotFoundException::class.java){
			userService.findById(100)
		}

		assertEquals("User with id 100 doesn't exist",error.message)
	}

	@Test
	fun `gettingShouldFailIfEmailDoesn'tExist`(){
		Mockito.`when`(userRepository.findByEmail("100@gmail.com")).thenReturn(Optional.empty())

		var error = assertThrows(NotFoundException::class.java){
			userService.findByEmail("100@gmail.com")
		}

		assertEquals("User with email 100@gmail.com doesn't exist",error.message)
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameWallet(){
		var userOne : User = userBuilder.withWallet("12345678").build()
		var userTwo : User = userBuilder.withWallet("12345678").build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)
		userService.save(userOne)
		Mockito.`when`(userRepository.save(userTwo)).thenThrow(DataIntegrityViolationException::class.java)

		var error = assertThrows(DuplicateUniqueException::class.java){
			userService.save(userTwo)
		}

		assertEquals("Wallet ${userOne.wallet} or Email ${userOne.email} already exist",error.message)
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameEmail(){
		var userOne : User = userBuilder.withEmail("userOne@gmail.com").build()
		var userTwo : User = userBuilder.withEmail("userOne@gmail.com").build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)
		userService.save(userOne)
		Mockito.`when`(userRepository.save(userTwo)).thenThrow(DataIntegrityViolationException::class.java)

		var error = assertThrows(DuplicateUniqueException::class.java) {
			userService.save(userTwo)
		}

		assertEquals("Wallet ${userOne.wallet} or Email ${userOne.email} already exist",error.message)

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

	@Test
	fun addTransactionsToUser(){
		var userOne = userBuilder.build()

		Mockito.`when`(userRepository.save(userOne)).thenReturn(userOne)

		var userWhoAddATransaction = userService.addTransactionTo(userOne,transaciton)

		assertEquals(userWhoAddATransaction.transactions.size,1)
		assertEquals(userWhoAddATransaction.operations,1)
	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
