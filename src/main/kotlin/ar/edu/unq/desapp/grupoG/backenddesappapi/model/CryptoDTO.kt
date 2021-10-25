package ar.edu.unq.desapp.grupoG.backenddesappapi.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CryptoDTO(var symbol : String, var price : Double, var date: String = LocalDate.now().format(
    DateTimeFormatter.ofPattern("dd/MM/yyyy")), var hour:String = LocalDateTime.now().toLocalTime().format(
    DateTimeFormatter.ofPattern("HH:mm"))): Serializable {

}