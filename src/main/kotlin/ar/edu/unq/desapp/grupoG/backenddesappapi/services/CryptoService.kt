package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.CryptoDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CryptoService {

    @Autowired
    lateinit var restTemplate : RestTemplate

    fun quote(cryptoName : String) : CryptoDTO{
        var response = restTemplate.getForEntity("https://api1.binance.com/api/v3/ticker/price?symbol=${cryptoName}",CryptoDTO::class.java)
        return response.body!!
    }
}
