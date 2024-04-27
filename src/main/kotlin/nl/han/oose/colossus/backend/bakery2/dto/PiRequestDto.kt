package nl.han.oose.colossus.backend.bakery2.dto

class PiRequestDto {

    private var id : Int = 0
    private var requestedOn: String = ""
    private var macAdress: String = ""


    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }
    fun getRequestedOn(): String {
        return this.requestedOn
    }

    fun setRequestedOn(requestedOn: String) {
        this.requestedOn = requestedOn
    }

    fun getMacAdress(): String {
        return this.macAdress
    }

    fun setMacAdress(macAdress: String) {
        this.macAdress = macAdress
    }

}