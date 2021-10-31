package ar.edu.unq.desapp.grupoG.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface TransactionRepository : JpaRepository<Transaction,Long> {

    fun findByStateContaining(state : String) : Optional<List<Transaction>>

    @Modifying
    @Query("UPDATE transaction t SET state = 'En progreso' WHERE t.id = ?1 ",nativeQuery = true)
    fun updateActivityToInProgress(id:Long)

    @Modifying
    @Query("UPDATE transaction t SET state = 'Creada',counter_Part_User = null WHERE t.id = ?1 ", nativeQuery = true)
    fun cancelActivity(id: Long)
}