package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.UserDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Column(length = 30) var name : String, @Column(length = 30) var surname : String,
                @Column(unique = true) var email : String, @Column(length = 30) var address : String,
                @Column var password : String, @Column(length = 22) var cvu : String,
                @Column(length = 8,unique = true) var wallet : String,
                @Column var points : Int = 0, @Column var operations : Int = 0) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

    @OneToMany(mappedBy = "user",cascade = arrayOf(CascadeType.ALL))
    @JsonIgnore
    var transactions : MutableList<Transaction> = mutableListOf()

    private fun newOperation() {
        operations++
    }

    fun addTransaction(tr : Transaction){
        this.transactions.add(tr)
        newOperation()
    }
    fun reputation():String{
        return if(operations == 0) "Sin operaciones" else (this.points / this.operations).toString()
    }
    fun toDTO() : UserDTO {
        return UserDTO(name,surname,email,address,cvu,wallet,points,operations,this.reputation())
    }

    fun discountPoints(){
        this.points-=20
    }

    fun sumPoints(points:Int){
        this.points+= points
    }

    fun deleteTransaction(transaction: Transaction){
        var result = transactions.remove(transaction)
        if(result)
            operations--
    }


}