package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.persistence.*
import kotlin.math.absoluteValue

@Entity
class Transaction(@ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "user_id") var user: User,
                  @Column var hour : String = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                  @Column val cryptoName : String, @Column val unitValue : Double, @Column val quote : Double,
                  @Column val totalPrice : Double, @Column val amount : Double, @Column val type : String, @Column var state:String,
                  @Column var counterPartUser: String? = null) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

    fun toDTO() : TransactionDTO{
        return TransactionDTO(id,user.email,hour,cryptoName,unitValue,quote,totalPrice,amount,type,user.cvu,user.wallet,state,user.reputation(),counterPartUser)
    }

    fun getPointsForUsers() : Int{
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        var actualHour = LocalDateTime.now().toLocalTime().format(formatter)
        val actualTime = LocalTime.parse(actualHour,formatter)
        val timeOfCreation = LocalTime.parse(hour,formatter)

        val differencesBetweenHours = actualTime.hour - timeOfCreation.hour
        val differencesBetweenMinutes = actualTime.minute - timeOfCreation.minute

        if(differencesBetweenHours.absoluteValue > 0 || differencesBetweenMinutes > 30)
            return 5
        return 10
    }
}