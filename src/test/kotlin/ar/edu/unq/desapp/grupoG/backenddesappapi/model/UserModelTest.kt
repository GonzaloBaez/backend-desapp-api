package ar.edu.unq.desapp.grupoG.backenddesappapi.model


import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.UserBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserModelTest {
    private lateinit var userBuilder : UserBuilder

    @Mock
    private lateinit var transaction : Transaction

    @Before
    fun setUp(){
        userBuilder = UserBuilder()
    }

    @Test
    fun aNewUserHasZeroOperationsAndZeroPoints(){
        var newUser = userBuilder.build()

        assertEquals(newUser.operations,0)
        assertEquals(newUser.points,0)
    }

    @Test
    fun whenANewUserAddATransactionHeHasOneOperation(){
        var newUser = userBuilder.build()

        newUser.addTransaction(transaction)

        assertEquals(newUser.operations,1)
        assertEquals(newUser.transactions.size,1)
        assertTrue(newUser.transactions.contains(transaction))
    }

    @Test
    fun whenUserHasZeroOperationsHeHasNoReputationOnDTO(){
        var newUser = userBuilder.build()
        var userDTO = newUser.toDTO()

        assertEquals(userDTO.operations,0)
        assertEquals(userDTO.reputation,"Sin operaciones")
    }

    @Test
    fun whenUserHasOperationsHeHasSomeReputationOnDTO(){
        var newUser = userBuilder.withPoints(10).build()

        newUser.addTransaction(transaction)
        newUser.addTransaction(transaction)
        var userDTO = newUser.toDTO()

        assertEquals(userDTO.operations,2)
        assertEquals(userDTO.reputation,"5")
    }
}