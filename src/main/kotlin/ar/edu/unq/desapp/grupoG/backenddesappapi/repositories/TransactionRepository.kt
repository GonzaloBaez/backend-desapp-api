package ar.edu.unq.desapp.grupoG.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface TransactionRepository : JpaRepository<Transaction,Long> {
}