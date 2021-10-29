package ar.edu.unq.desapp.grupoG.backenddesappapi

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.DollarBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.DollarQuoteDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.DollarQuoteService
import org.assertj.core.api.Assertions.assertThat
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
        var dollar1 = dollarBuilder.build()
        var dollar2 = dollarBuilder.build()

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0OTEwODQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJnb256YW9oQGdtYWlsLmNvbSJ9.frur67tbWkReKM8PeXbIJ0BwfpgT2NCoBd6LtojdKQY7x5IMnnQzE5fxEFF2MBApqSqRGPzCQgtBpXjo-xl6hw")
        var entity = HttpEntity("parameters",headers)

        var allDollarQuotes = listOf(dollar2,dollar1)
        Mockito.`when`(restTemplate.exchange("https://api.estadisticasbcra.com/usd",
            HttpMethod.GET, entity, object : ParameterizedTypeReference<List<DollarQuoteDTO>>() {}))
            .thenReturn(ResponseEntity(allDollarQuotes,HttpStatus.OK))
        var dollarObtained = dollarService.quote()

        assertThat(dollar1 == dollarObtained)

    }

}