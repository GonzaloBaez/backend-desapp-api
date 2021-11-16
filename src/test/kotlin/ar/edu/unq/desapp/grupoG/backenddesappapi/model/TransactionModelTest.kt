package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.TransactionBuilder
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TransactionModelTest {

    lateinit var transactionBuilder: TransactionBuilder
    lateinit var transaction: Transaction

    @Before
    fun setUp(){
        transactionBuilder = TransactionBuilder()
    }

    @Test
    fun whenTransactionCreatedLessThan31MinutesAgoItShouldReturn10PointsOnGetPointsForUsers(){
        transaction = transactionBuilder.build()

        assertEquals(10,transaction.getPointsForUsers())
    }

    @Test
    fun whenTransactionCreatedMoreThan30MinutesAgoItShouldReturn5PointsOnGetPointsForUsers(){
        var actual = LocalTime.now().minusMinutes(31)

        transaction = transactionBuilder.build()
        transaction.hour = actual.format(DateTimeFormatter.ofPattern("HH:mm"))

        assertEquals(5,transaction.getPointsForUsers())
    }
}