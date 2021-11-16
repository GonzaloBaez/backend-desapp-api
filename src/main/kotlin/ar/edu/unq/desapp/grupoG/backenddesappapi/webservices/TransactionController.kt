package ar.edu.unq.desapp.grupoG.backenddesappapi.webservices

import ar.edu.unq.desapp.grupoG.backenddesappapi.aspects.LogAudit
import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions.BadRequest
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
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

    @LogAudit
    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionDTO): ResponseEntity<Any> {
        var user = userService.findByEmail(transactionDTO.user)
        var transaction = transactionDTO.toModel(user)

        var savedUser = userService.addTransactionTo(user, transaction)

        val responseHeader = HttpHeaders()
        responseHeader.set("location", "/api/transaction" + "/" + transaction.id)
        return ResponseEntity(savedUser.transactions.last().toDTO(), HttpStatus.CREATED)
    }

    @LogAudit
    @GetMapping
    fun allTransactions(): List<TransactionDTO> {
        return transactionService.findAll().map { it.toDTO() }
    }

    @LogAudit
    @GetMapping("/activities")
    fun allActivities(): List<TransactionDTO> {
        return transactionService.findByState("Creada").map { it.toDTO() }
    }

    @LogAudit
    @PutMapping("/activity-{id}-{counterPartUser}/update")
    fun updateActivityToInProgress(@PathVariable("id") id:Long, @PathVariable("counterPartUser") counterPartUser:String):ResponseEntity<Any>{
        var transaction = transactionService.findById(id)
        var counterUser = userService.findByEmail(counterPartUser)
        checkTransactionToPutInProgress(transaction,counterUser)
        transactionService.setCounterPartUser(transaction,counterPartUser)
        transactionService.updateActivityToInProgress(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @LogAudit
    @GetMapping("/pending-activities/{userEmail}")
    fun activitiesFromCounterPartUser(@PathVariable("userEmail") userEmail:String):List<TransactionDTO> {
        return transactionService.findByCounterPartUser(userEmail).map { it.toDTO() }
    }

    @LogAudit
    @PutMapping("/activity-{id}-{cancelingUser}/delete")
    fun cancelActivity(@PathVariable("id") id:Long, @PathVariable("cancelingUser") cancelingUser:String): ResponseEntity<Any>{
        transactionService.cancelActivity(id)
        userService.discountToCancelingUser(cancelingUser)
        return ResponseEntity(HttpStatus.OK)
    }

    @LogAudit
    @DeleteMapping("/activity-{id}/{cancelingUser}/delete")
    fun deleteTransaction(@PathVariable("id") id:Long,@PathVariable("cancelingUser") cancelingUser: String) : ResponseEntity<Any>{
        var transaction = transactionService.findById(id)
        checkTransactionToDelete(transaction,cancelingUser)
        userService.discountToCancelingUser(cancelingUser)
        transactionService.deleteTransaction(transaction)
        userService.deleteTransaction(cancelingUser,transaction)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @LogAudit
    @PutMapping("/close/{id}/{counterPartUserEmail}")
    fun closeActivity(@PathVariable("id") id:Long, @PathVariable("counterPartUserEmail") counterPartUserEmail: String):ResponseEntity<Any>{
        var transaction = transactionService.findById(id)
        checkCloseCondition(transaction)
        userService.closeActivity(transaction,counterPartUserEmail)
        return ResponseEntity(HttpStatus.OK)
    }

    @LogAudit
    @GetMapping("/activities/{userEmail}")
    fun getActivitiesByUser(@PathVariable("userEmail") userEmail:String): ResponseEntity<Any>{
        var activities = userService.getActivitiesFromUser(userEmail)
        var activitiesDTO = activities.map { it.toDTO() }
        return ResponseEntity(activitiesDTO,HttpStatus.OK)
    }

    private fun checkTransactionToPutInProgress(transaction : Transaction, user : User){
        require(transaction.counterPartUser == null && transaction.state == "Creada" &&
        transaction.user.email != user.email){
            throw BadRequest("Can't operate with transaction with id ${transaction.id}")
        }
    }

    private fun checkCloseCondition(transaction: Transaction){
        require(transaction.counterPartUser != null && transaction.state == "En progreso"){
            throw BadRequest("Can't close transaction with id ${transaction.id}")
        }
    }

    private fun checkTransactionToDelete(transaction: Transaction,userEmail:String){
        require(transaction.user.email == userEmail && transaction.state != "Cerrada"){
            throw BadRequest("Transaction already closed or it belong to another user")
        }
    }
}
