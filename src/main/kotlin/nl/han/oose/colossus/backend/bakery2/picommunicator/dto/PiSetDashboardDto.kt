package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiSetDashboardDto {

    private var url: String = ""
    private var refresh: Int = 0

    fun getUrl(): String {
        return this.url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getRefresh(): Int {
        return refresh
    }

    fun setRefresh(refresh: Int) {
        this.refresh = refresh
    }
}