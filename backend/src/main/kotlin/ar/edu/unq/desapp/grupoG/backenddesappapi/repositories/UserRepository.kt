package ar.edu.unq.desapp.grupoG.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
 interface UserRepository: JpaRepository<User, Long> {

     override fun findById(id:Long) : Optional<User>
}