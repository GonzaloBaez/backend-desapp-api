package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.TransactionService
import ar.edu.unq.desapp.grupoG.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/transaction")
@CrossOrigin
class TransactionController {

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionDTO): ResponseEntity<Any> {
        var user = userService.findByEmail(transactionDTO.user)
        var transaction = transactionDTO.toModel(user)

        var savedTransaction = transactionService.save(transaction)
        userService.addTransactionTo(user, transaction)

        val responseHeader = HttpHeaders()
        responseHeader.set("location", "/api/transaction" + "/" + transaction.id)
        return ResponseEntity(savedTransaction.toDTO(), HttpStatus.CREATED)
    }

    @GetMapping
    fun allTransactions(): List<TransactionDTO> {
        return transactionService.findAll().map { it.toDTO() }
    }

    @GetMapping("/activities")
    fun allActivities(): List<TransactionDTO> {
        return transactionService.findByState("Creada").map { it.toDTO() }
    }

    @PutMapping("/activity-{id}-{counterPartUser}/update")
    fun updateActivityToInProgress(@PathVariable("id") id:Long, @PathVariable("counterPartUser") counterPartUser:String):ResponseEntity<Any>{
        transactionService.updateActivityToInProgress(id)
        transactionService.setCounterPartUser(id,counterPartUser)
        return ResponseEntity(HttpStatus.OK)
    }
}