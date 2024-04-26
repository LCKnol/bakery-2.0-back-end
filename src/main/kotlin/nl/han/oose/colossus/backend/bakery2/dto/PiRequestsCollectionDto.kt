package nl.han.oose.colossus.backend.bakery2.dto

class PiRequestsCollectionDto {

    private var piRequests: ArrayList<PiRequestDto> = ArrayList()

    fun setPiRequests(newValue: ArrayList<PiRequestDto>) {
        this.piRequests = newValue
    }

    fun getPiRequests(): ArrayList<PiRequestDto> {
        return this.piRequests
    }
}