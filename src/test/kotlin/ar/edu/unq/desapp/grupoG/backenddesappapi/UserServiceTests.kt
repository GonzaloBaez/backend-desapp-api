package ar.edu.unq.desapp.grupoG.backenddesappapi
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.After

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTests {

	@Autowired
	lateinit var userService: UserService

	@Test
	fun registerAUserAndGettingIt() {
		var userToRegister:User = User("Gonzalo","b","c","d","3","a","w")
		userService.save(userToRegister)
		var registeredUser:User= userService.findById(userToRegister.id)

		assertThat(userToRegister == registeredUser)
	}

	@After
	fun clear(){
		userService.clearDatabase()
	}
}
