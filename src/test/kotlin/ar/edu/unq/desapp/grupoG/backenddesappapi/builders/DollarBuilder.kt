package ar.edu.unq.desapp.grupoG.backenddesappapi.builders

import ar.edu.unq.desapp.grupoG.backenddesappapi.model.DollarQuoteDTO

class DollarBuilder(
    var d : String = "2/10/2021",
    var v : Double = 185.0
) {
    fun build() = DollarQuoteDTO(d,v)

    fun withD(d:String) : DollarBuilder{
        this.d = d
        return this
    }

    fun withV(v:Double):DollarBuilder{
        this.v = v
        return this
    }
}