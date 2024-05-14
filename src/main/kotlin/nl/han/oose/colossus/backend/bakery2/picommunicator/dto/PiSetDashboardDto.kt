package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiSetDashboardDto {

    private var url : String = ""

    fun getUrl(): String {
        return this.url
    }

    fun setUrl(url: String) {
        this.url = url
    }
}