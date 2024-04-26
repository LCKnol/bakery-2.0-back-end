package nl.han.oose.colossus.backend.bakery2.dto

class PiRequestDto {

    private var id : Int = 0
    private var requestTime: String = ""
    private var macAdress: String = ""


    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }
    fun getRequestTime(): String {
        return this.requestTime
    }

    fun setRequestTime(requestTime: String) {
        this.requestTime = requestTime
    }

    fun getMacAdress(): String {
        return this.macAdress
    }

    fun setMacAdress(macAdress: String) {
        this.macAdress = macAdress
    }

}