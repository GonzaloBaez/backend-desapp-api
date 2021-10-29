package ar.edu.unq.desapp.grupoG.backenddesappapi.builders

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.User

class UserBuilder(
    var name : String = "NameX",
    var surname : String = "SurnameY",
    var email : String = "z@gmail.com",
    var address : String = "yyyy yyy yyy",
    var password : String = "password",
    var cvu : String = "cccccccccccccccccccccc",
    var wallet : String = "zzzzzzzz",
    var points : Int = 0
) {

    fun build() = User(name, surname, email, address, password, cvu, wallet,points)

    fun withWallet(wallet : String) : UserBuilder {
        this.wallet = wallet
        return this
    }

    fun withEmail(email:String):UserBuilder{
        this.email = email
        return this
    }

    fun withPassword(password: String): UserBuilder{
        this.password = password
        return this
    }

    fun withPoints(points:Int):UserBuilder{
        this.points = points
        return this
    }
}