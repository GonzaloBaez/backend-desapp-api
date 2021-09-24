package ar.edu.unq.desapp.grupoG.backenddesappapi.model

class UserBuilder {
    var name = "NameX"
    var surname = "SurnameY"
    var email = "z@gmail.com"
    var address = "yyyy yyy yyy"
    var password = "password"
    var cvu = "cccccccccccccccccccccc"
    var wallet = "zzzzzzzz"

    fun anyUser() : User {
        return User(name,surname,email,address,password,cvu,wallet)
    }


    fun withWallet(wallet : String) : User {
        return User(name,surname,email,address,password,cvu,wallet)
    }
}