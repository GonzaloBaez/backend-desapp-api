package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Column var name : String, @Column var surname : String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0


}