package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.builders.DollarBuilder
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.DollarQuoteService
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus


@RunWith(MockitoJUnitRunner::class)
class DollarQuoteControllerTest {

    @Mock
    lateinit var dollarService : DollarQuoteService

    @InjectMocks
    lateinit var dollarController : DollarQuoteController

    @Test
    fun gettingDollarQuote(){
        var quote = DollarBuilder().build()

        Mockito.`when`(dollarService.quote()).thenReturn(quote)

        var obtainedDollar = dollarController.quote()

        assertThat(obtainedDollar.body == quote)
        assertThat(obtainedDollar.statusCode == HttpStatus.OK)
    }
}