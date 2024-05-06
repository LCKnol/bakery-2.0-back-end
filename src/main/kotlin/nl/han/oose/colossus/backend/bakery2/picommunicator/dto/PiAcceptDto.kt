package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiAcceptDto {

    private var macAddress: String = ""
    private var isAccepted = false

    fun getmacAddress(): String {
        return this.macAddress
    }

    fun setmacAddress(macAddress: String) {
        this.macAddress = macAddress
    }

    fun getIsAccepted(): Boolean {
        return this.isAccepted
    }

    fun setIsAccepted(isAccepted: Boolean) {
        this.isAccepted = isAccepted
    }
}