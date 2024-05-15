package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiSignUpRequestDto {

    private var macAddress: String = ""
    private var ipAddress: String = ""

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