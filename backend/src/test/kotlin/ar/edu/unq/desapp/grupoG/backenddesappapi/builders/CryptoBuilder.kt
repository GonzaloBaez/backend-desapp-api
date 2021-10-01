package ar.edu.unq.desapp.grupoG.backenddesappapi.builders

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.CryptoDTO

class CryptoBuilder(
    var symbol : String = "BNDUSDT",
    var price : Double = 375.0
) {

    fun build() = CryptoDTO(symbol,price)

}