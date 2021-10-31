package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.TransactionDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
class Transaction(@ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "user_id") var user: User,
                  @Column val hour : String = LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                  @Column val cryptoName : String, @Column val unitValue : Double, @Column val quote : Double,
                  @Column val totalPrice : Double, @Column val amount : Double, @Column val type : String, @Column var state:String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

    fun toDTO() : TransactionDTO{
        return TransactionDTO(id,user.email,hour,cryptoName,unitValue,quote,totalPrice,amount,type,user.cvu,user.wallet,state,user.reputation())
    }
}