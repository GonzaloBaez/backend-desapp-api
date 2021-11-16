package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.BadRequest
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateUniqueException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.repositories.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
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
	lateinit var transaction : Transaction

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

		var userWhoAddATransaction = userService.addTransactionTo(userOne,transaction)

		assertEquals(1,userWhoAddATransaction.transactions.size)
		assertEquals(1,userWhoAddATransaction.operations)
		assertTrue(userOne.transactions.contains(transaction))
	}

	@Test
	fun discountPointsToCancelingUser(){
		var userOne = userBuilder.withEmail("z2@gmail.com").withPoints(50).build()
		var oldPoints = userOne.points

		Mockito.`when`(userRepository.findByEmail(userOne.email)).thenReturn(Optional.of(userOne))

		userService.discountToCancelingUser("z2@gmail.com")

		assertEquals(userOne.points,30)
		assertNotEquals(userOne.points,oldPoints)
	}

	@Test
	fun getActivitiesFromUser(){
		var user = userBuilder.withEmail("z2@gmail.com").build()

		Mockito.`when`(userRepository.save(user)).thenReturn(user)
		Mockito.`when`(userRepository.findByEmail(user.email)).thenReturn(Optional.of(user))
		userService.addTransactionTo(user,transaction)

		var activitiesFromUser = userService.getActivitiesFromUser(user.email)

		assertEquals(1,user.transactions.size)
		assertEquals(activitiesFromUser,user.transactions)
	}

	@Test
	fun closeActivityFromUserCorrectly(){
		var user = userBuilder.build()
		var otherUser = userBuilder.withEmail("saraza").build()
		user.addTransaction(transaction)
		var olderPointsUser = user.points
		var olderPointsOtherUser = otherUser.points

		Mockito.`when`(userRepository.findByEmail("saraza")).thenReturn(Optional.of(otherUser))
		Mockito.`when`(transaction.getPointsForUsers()).thenReturn(10)
		Mockito.`when`(transaction.user).thenReturn(user)
		Mockito.`when`(transaction.counterPartUser).thenReturn("saraza")

		userService.closeActivity(transaction,"saraza")

		Mockito.verify(userRepository,times(1)).saveAll(listOf(user,otherUser))
		assertEquals(user.points,10)
		assertEquals(otherUser.points,10)
		assertNotEquals(user.points,olderPointsUser)
		assertNotEquals(otherUser.points,olderPointsOtherUser)
	}

	@Test
	fun closeActivityFailsWithBadRequestIfCounterpartUserIsDifferentInParam(){
		var user = userBuilder.build()
		var otherUser = userBuilder.withEmail("saraza").build()
		user.addTransaction(transaction)

		Mockito.`when`(transaction.counterPartUser).thenReturn("otherThing")
		Mockito.`when`(userRepository.findByEmail("saraza")).thenReturn(Optional.of(otherUser))

		var error = assertThrows(BadRequest::class.java){
			userService.closeActivity(transaction,otherUser.email)
		}

		assertEquals("Transaction with id 0 belongs to another user",error.message)
		assertEquals(0,user.points)
		assertEquals(0,otherUser.points)
	}

	@Test
	fun deleteTransactionFromUser(){
		var user = userBuilder.withEmail("saraza@gmail.com").build()
		user.addTransaction(transaction)
		val oldOperations = user.operations

		Mockito.`when`(userRepository.findByEmail("saraza@gmail.com")).thenReturn(Optional.of(user))

		userService.deleteTransaction("saraza@gmail.com",transaction)

		assertEquals(0,user.transactions.size)
		assertEquals(0,user.operations)
		assertEquals(1,oldOperations)
		Mockito.verify(userRepository, times(1)).save(user)
	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
