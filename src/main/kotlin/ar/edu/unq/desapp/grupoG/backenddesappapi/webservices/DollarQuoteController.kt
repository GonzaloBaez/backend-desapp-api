package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.aspects.LogAudit
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.DollarQuoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dollar/")
@CrossOrigin
class DollarQuoteController {

    @Autowired
    lateinit var dollarQuoteService:DollarQuoteService

    @LogAudit
    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping
    fun quote(): ResponseEntity<Any> {
        return ResponseEntity(dollarQuoteService.quote(), HttpStatus.OK)
    }
}