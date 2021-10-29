package ar.edu.unq.desapp.grupoG.backenddesappapi

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.CryptoBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.CryptoDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.CryptoService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@RunWith(MockitoJUnitRunner::class)
class CryptoServiceTest {

    lateinit var cryptoBuilder : CryptoBuilder

    @Mock
    lateinit var restTemplate : RestTemplate

    @InjectMocks
    lateinit var cryptoService : CryptoService

    @Before
    fun setUp(){
        cryptoBuilder = CryptoBuilder()
    }

    @Test
    fun gettingBNBUSDTCrypto(){
        var crypto = cryptoBuilder.build()

        Mockito.`when`(restTemplate.getForEntity("https://api1.binance.com/api/v3/ticker/price?symbol=${crypto.symbol}",CryptoDTO::class.java))
            .thenReturn(ResponseEntity(crypto,HttpStatus.OK))

        var crypto2 = cryptoService.quote(crypto.symbol)

        assertThat(crypto == crypto2)

    }

    @Test
    fun gettingAllCryptos(){
        var cryp1 = cryptoBuilder.build()
        var cryp2 = cryptoBuilder.build()

        var allCryptos = listOf(cryp1,cryp2)

        Mockito.`when`(restTemplate.exchange("https://api1.binance.com/api/v3/ticker/price",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<CryptoDTO>>() {}))
            .thenReturn(ResponseEntity(allCryptos,HttpStatus.OK))

        var allCryptos2 = cryptoService.allCryptos()

        assertThat(allCryptos == allCryptos2)
    }
}