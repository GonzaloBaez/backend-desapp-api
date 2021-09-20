package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Column(length = 30) var name : String, @Column(length = 30) var surname : String,
                @Column var email : String, @Column(length = 30) var direccion : String,
                @Column var contraseña : String, @Column(length = 22) var cvu : String,
                @Column(length = 8) var wallet : String ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0


}