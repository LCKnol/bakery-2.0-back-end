package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiSignUpRequestDto {

    private var macAddress : String = ""

    fun getMacAddress(): String {
        return this.macAddress
    }

    fun setMacAddress(macAddress: String) {
        this.macAddress= macAddress
    }
}