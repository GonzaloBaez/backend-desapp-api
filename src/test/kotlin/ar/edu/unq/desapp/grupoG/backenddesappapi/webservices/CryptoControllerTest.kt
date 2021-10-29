package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.CryptoBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.CryptoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

@RunWith(MockitoJUnitRunner::class)
class CryptoControllerTest {

    var cryptoBuilder = CryptoBuilder()

    @Mock
    lateinit var cryptoService: CryptoService

    @InjectMocks
    lateinit var cryptoController: CryptoController

    @Test
    fun gettingCryptoQuoteWithName(){
        var cryptoBND = cryptoBuilder.build()

        Mockito.`when`(cryptoService.quote("BNDUSDT")).thenReturn(cryptoBND)

        val obtainedCrypto = cryptoController.quote("BNDUSDT")

        assertEquals(obtainedCrypto.body,cryptoBND)
        assertEquals(obtainedCrypto.statusCode,HttpStatus.OK)

    }

    @Test
    fun gettingAllCryptos(){
        var cryptoOne = cryptoBuilder.build()
        var cryptoTwo = cryptoBuilder.build()

        val createdCryptos = mutableListOf(cryptoOne,cryptoTwo)

        Mockito.`when`(cryptoService.allCryptos()).thenReturn(mutableListOf(cryptoOne,cryptoTwo))

        val allCryptos = cryptoController.allCryptos()

        assertEquals(createdCryptos,allCryptos.body)
    }
}