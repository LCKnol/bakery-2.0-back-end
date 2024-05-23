package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiPingDto {
    private var macAddress: String = ""

    fun setMacAddress(macAddress: String) {
        this.macAddress = macAddress
    }

    fun getMacAddress(): String {
        return this.macAddress
    }
}