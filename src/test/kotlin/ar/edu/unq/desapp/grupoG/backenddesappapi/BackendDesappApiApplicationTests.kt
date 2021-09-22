package ar.edu.unq.desapp.grupoG.backenddesappapi

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BackendDesappApiApplicationTests {

	lateinit var userService: UserService

	@Test
	fun savingAUserAndGettingIt() {
		var user = User("1","2","3","4","5","6","7")
		println("Usuario ${user}")
	}
}
