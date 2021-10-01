package ar.edu.unq.desapp.grupoG.backenddesappapi.services

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.CryptoDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CryptoService {

    var cryptos : MutableList<String> = mutableListOf("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT", "DOTUSDT",
                                                       "ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT","ADAUSDT","TRXUSDT","AUDIOUSDT")

    @Autowired
    lateinit var restTemplate : RestTemplate


    fun quote(cryptoName : String) : CryptoDTO{
        var response = restTemplate.getForEntity("https://api1.binance.com/api/v3/ticker/price?symbol=${cryptoName}",CryptoDTO::class.java)
        return response.body!!
    }

    fun allCryptos() : List<CryptoDTO>{
          var response = restTemplate.exchange("https://api1.binance.com/api/v3/ticker/price",
              HttpMethod.GET, null, object : ParameterizedTypeReference<List<CryptoDTO>>() {})

        var list : List<CryptoDTO> = response.body!!

        return list.filter { cryptos.contains(it.symbol) }
    }



}
