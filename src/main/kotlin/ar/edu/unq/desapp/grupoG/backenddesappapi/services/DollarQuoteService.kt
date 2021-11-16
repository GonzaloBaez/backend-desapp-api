package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.DollarQuoteDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DollarQuoteService {

    @Autowired
    lateinit var restTemplate: RestTemplate
    var key = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0OTEwODQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJnb256YW9oQGdtYWlsLmNvbSJ9.frur67tbWkReKM8PeXbIJ0BwfpgT2NCoBd6LtojdKQY7x5IMnnQzE5fxEFF2MBApqSqRGPzCQgtBpXjo-xl6hw"

    @Cacheable("cacheDollar")
    fun quote(): DollarQuoteDTO {
        /*var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer $key")
        var entity = HttpEntity("parameters",headers)
        var response = restTemplate.exchange("https://api.estadisticasbcra.com/usd",HttpMethod.GET,entity,object : ParameterizedTypeReference<List<DollarQuoteDTO>>() {})
        return response.body!!.last()*/
        var response = restTemplate.exchange("https://apis.datos.gob.ar/series/api/series/?ids=168.1_T_CAMBIOR_D_0_0_26&start_date=2021-07&limit=5000",HttpMethod.GET,null,
            object:ParameterizedTypeReference<Any>(){})

        var responseMap = response.body as HashMap<Any,Any>

        var listReponse = responseMap["data"] as ArrayList<Any>

        var objectReponse = listReponse.last() as List<Any>

        return DollarQuoteDTO(objectReponse[0] as String,objectReponse[1] as Double)
    }
}