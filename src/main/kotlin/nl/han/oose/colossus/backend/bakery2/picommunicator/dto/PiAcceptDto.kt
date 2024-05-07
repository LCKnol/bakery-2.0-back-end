package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiAcceptDto {

    private var isAccepted = false

    fun getIsAccepted(): Boolean {
        return this.isAccepted
    }

    fun setIsAccepted(isAccepted: Boolean) {
        this.isAccepted = isAccepted
    }
}