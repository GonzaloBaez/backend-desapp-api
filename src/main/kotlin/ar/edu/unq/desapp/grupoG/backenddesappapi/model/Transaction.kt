package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*
import kotlin.math.absoluteValue

@Entity
class Transaction(@ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "user_id") var user: User,
                  @Column val hour : String = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
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
        var actualHour = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")).filter { it != ':' }
        var hourOfCreation = hour.filter { it != ':' }

        var differencesBetweenHours = actualHour.toInt() - hourOfCreation.toInt()
        if(differencesBetweenHours.absoluteValue>30)
            return 5
        return 10
    }
}