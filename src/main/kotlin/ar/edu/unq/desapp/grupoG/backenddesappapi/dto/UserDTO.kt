package ar.edu.unq.desapp.grupoG.backenddesappapi.dto

data class UserDTO(var name:String, var surname: String,var email : String, var address : String,
                   var cvu : String, var wallet : String, var points : Int, var operations : Int, var reputation : String) {
}