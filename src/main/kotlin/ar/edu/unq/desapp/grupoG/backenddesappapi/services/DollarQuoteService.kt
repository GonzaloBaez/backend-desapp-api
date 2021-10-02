package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.DollarQuoteDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DollarQuoteService {

    @Autowired
    lateinit var restTemplate: RestTemplate
    var key = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0OTEwODQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJnb256YW9oQGdtYWlsLmNvbSJ9.frur67tbWkReKM8PeXbIJ0BwfpgT2NCoBd6LtojdKQY7x5IMnnQzE5fxEFF2MBApqSqRGPzCQgtBpXjo-xl6hw"

    fun quote(): DollarQuoteDTO {
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer $key")
        var entity = HttpEntity("parameters",headers)
        var response = restTemplate.exchange("https://api.estadisticasbcra.com/usd",HttpMethod.GET,entity,object : ParameterizedTypeReference<List<DollarQuoteDTO>>() {})
        return response.body!!.last()
    }
}