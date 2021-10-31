package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.DollarBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

@RunWith(MockitoJUnitRunner::class)
class DollarServiceTest {

    lateinit var dollarBuilder : DollarBuilder

    @Mock
    lateinit var restTemplate : RestTemplate

    @InjectMocks
    lateinit var dollarService : DollarQuoteService

    @Before
    fun setUp(){
        dollarBuilder = DollarBuilder()
    }

   @Test
    fun gettingDollarQuote(){
        var dollar1 = arrayListOf("20/10/05",105.2)
        var dollar2 = arrayListOf("20/11/05",152.0)

        /*var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0OTEwODQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJnb256YW9oQGdtYWlsLmNvbSJ9.frur67tbWkReKM8PeXbIJ0BwfpgT2NCoBd6LtojdKQY7x5IMnnQzE5fxEFF2MBApqSqRGPzCQgtBpXjo-xl6hw")
        var entity = HttpEntity("parameters",headers)*/

        var allDollarQuotes : LinkedHashMap<String,Any> = LinkedHashMap()

        allDollarQuotes.set("data", arrayListOf(dollar1,dollar2))

        Mockito.`when`(restTemplate.exchange("https://apis.datos.gob.ar/series/api/series/?ids=168.1_T_CAMBIOR_D_0_0_26&start_date=2021-07&limit=5000",
            HttpMethod.GET, null, object : ParameterizedTypeReference<Any>() {}))
            .thenReturn(ResponseEntity(allDollarQuotes,HttpStatus.OK))

        var expectedDollar = dollarBuilder.withD("20/11/05").withV(152.0).build()
        var dollarObtained = dollarService.quote()

        assertEquals(expectedDollar.d,dollarObtained.d)
        assertEquals(expectedDollar.v,dollarObtained.v)
    }

}