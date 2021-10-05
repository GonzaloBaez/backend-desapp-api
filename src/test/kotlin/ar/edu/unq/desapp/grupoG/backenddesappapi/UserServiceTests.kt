package ar.edu.unq.desapp.grupoG.backenddesappapi

import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.NotFoundException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.After
import org.junit.Before
import org.springframework.dao.DataIntegrityViolationException

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTests {

	private lateinit var userBuilder : UserBuilder

	@Autowired
	lateinit var userService: UserService

	@Before
	fun setUp(){
		this.userBuilder = UserBuilder()
	}

	@Test
	fun savingAUserAndGettingIt() {
		var userToRegister:User = userBuilder.build()
		userService.save(userToRegister)
		var registeredUser:User= userService.findById(userToRegister.id)

		assertThat(userToRegister == registeredUser)
	}

	@Test
	fun gettingShouldFailIfIdDoesnotExist(){
		assertThatThrownBy { userService.findById(100) }.isInstanceOf(NotFoundException::class.java)
			.withFailMessage("User with id 100 doesn't exist")
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameWallet(){
		var userOne : User = userBuilder.withWallet("12345678").withEmail("asd@asd").build()
		var userTwo : User = userBuilder.withWallet("12345678").withEmail("asd2@asd").build()

		userService.save(userOne)

		assertThatThrownBy { userService.save(userTwo) }.isInstanceOf(DataIntegrityViolationException::class.java)

	}

	@Test
	fun gettingAllUsers(){
		var userOne = userBuilder.withWallet("12345678").withEmail("asd@asd").build()
		var userTwo = userBuilder.withWallet("04040400").withEmail("asd2@asd").build()
		userService.save(userOne)
		userService.save(userTwo)

		var allUsers = userService.findAll().toMutableList()

		assertThat(allUsers.size == 2)
	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
