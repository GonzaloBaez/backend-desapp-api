package ar.edu.unq.desapp.grupoG.backenddesappapi
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.DuplicateWalletException
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.UserBuilder
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
		var userToRegister:User = userBuilder.anyUser()
		userService.save(userToRegister)
		var registeredUser:User= userService.findById(userToRegister.id)

		assertThat(userToRegister == registeredUser)
	}

	@Test
	fun savingShouldFailIfTwoUsersHasSameWallet(){
		var userOne : User = userBuilder.withWallet("12345678")
		var userTwo : User = userBuilder.withWallet("12345678")

		userService.save(userOne)

		assertThatThrownBy { userService.save(userTwo) }.isInstanceOf(DataIntegrityViolationException::class.java)

	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
