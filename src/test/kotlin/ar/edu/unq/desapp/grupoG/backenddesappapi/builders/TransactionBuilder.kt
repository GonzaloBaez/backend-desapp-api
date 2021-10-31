package ar.edu.unq.desapp.grupoG.backenddesappapi.builders

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionBuilder(
    var user: User = UserBuilder().build(),
    val hour : String = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
    val cryptoName : String = "BTC", var unitValue : Double = 135.0, var quote : Double = 24975.0,
    var totalPrice : Double = 49950.0, var amount : Double = 2.0,
    var type : String = "Compra"
) {
    fun build() = Transaction(user,hour,cryptoName,unitValue,quote,totalPrice,amount,type)

    fun withUser(user : User) : TransactionBuilder{
        this.user = user
        return this
    }
}