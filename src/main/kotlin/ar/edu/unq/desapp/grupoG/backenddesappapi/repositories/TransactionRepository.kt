package ar.edu.unq.desapp.grupoG.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.swing.text.html.Option

@Configuration
@Repository
interface TransactionRepository : JpaRepository<Transaction,Long> {

    fun findByStateContaining(state : String) : Optional<List<Transaction>>

    @Modifying
    @Query("UPDATE transaction t SET state = 'En progreso' WHERE t.id = ?1 ",nativeQuery = true)
    fun updateActivityToInProgress(id:Long)

    @Query(" SELECT * From transaction t where t.user_id = ?1",nativeQuery = true)
    fun findByUserContaining(userId:Long): Optional<List<Transaction>>

    @Query("SELECT * from transaction t where t.counter_part_user = ?1",nativeQuery = true)
    fun findByCounterPartUserContaining(counterPartUser:String):Optional<List<Transaction>>

    @Modifying
    @Query("UPDATE transaction t SET state = 'Creada',counter_Part_User = null WHERE t.id = ?1 ", nativeQuery = true)
    fun cancelActivity(id: Long)
}