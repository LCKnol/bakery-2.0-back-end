package nl.han.oose.colossus.backend.bakery2.dto

class PiRequestDto {

    private var id: Int = 0
    private var requestedOn: String = ""
    private var macAddress: String = ""
    private var ipAddress: String = ""


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

    fun getMacAddress(): String {
        return this.macAddress
    }

    fun setMacAddress(macAddress: String) {
        this.macAddress = macAddress
    }

    fun getIpAddress(): String {
        return this.ipAddress
    }

    fun setIpAddress(ipAddress: String) {
        this.ipAddress = ipAddress
    }
}