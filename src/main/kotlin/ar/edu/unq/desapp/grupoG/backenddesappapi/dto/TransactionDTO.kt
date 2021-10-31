package ar.edu.unq.desapp.grupoG.backenddesappapi.dto

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionDTO(
    var user : String,
    val hour : String = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
    val cryptoName : String,
    var unitValue : Double,
    var quote : Double,
    var totalPrice : Double,
    var amount : Double,
    var type : String,
    var cvu : String? = null,
    var wallet : String? = null
) {

    fun toModel(user: User) : Transaction{
        return Transaction(user,hour,cryptoName,unitValue,quote,totalPrice,amount,type)
    }
}