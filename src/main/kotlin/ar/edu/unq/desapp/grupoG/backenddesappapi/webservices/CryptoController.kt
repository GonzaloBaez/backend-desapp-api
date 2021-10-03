package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.CryptoDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.CryptoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api/cryptos/")
class CryptoController {

    @Autowired
    lateinit var cryptoService : CryptoService

    @GetMapping("quote/{cryptoName}")
    fun quote(@PathVariable("cryptoName") cryptoName : String) : ResponseEntity<Any>{
        return ResponseEntity(cryptoService.quote(cryptoName),HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping("quotes")
    fun allCryptos() : ResponseEntity<List<CryptoDTO>>{
        return ResponseEntity(cryptoService.allCryptos(),HttpStatus.OK)
    }

    @Bean
    fun restTemplate() : RestTemplate {
        return RestTemplate()
    }
}