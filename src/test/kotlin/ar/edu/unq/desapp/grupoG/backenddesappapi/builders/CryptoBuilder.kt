package ar.edu.unq.desapp.grupoG.backenddesappapi.builders

import ar.edu.unq.desapp.grupoG.backenddesappapi.dto.CryptoDTO

class CryptoBuilder(
    var symbol : String = "BNBUSDT",
    var price : Double = 375.0
) {

    fun build() = CryptoDTO(symbol,price)

}