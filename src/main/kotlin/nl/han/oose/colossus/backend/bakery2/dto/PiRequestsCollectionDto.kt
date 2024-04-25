package nl.han.oose.colossus.backend.bakery2.dto

class PiRequestsCollectionDto {

    private var piRequests: ArrayList<PiRequestDto> = ArrayList()

    fun setDashboards(newValue: ArrayList<PiRequestDto>) {
        this.piRequests = newValue
    }

    fun getDashboards(): ArrayList<PiRequestDto> {
        return this.piRequests
    }
}