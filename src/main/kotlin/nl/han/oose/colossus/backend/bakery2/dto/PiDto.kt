package nl.han.oose.colossus.backend.bakery2.dto

class PiDto {
    private var id : Int = 0
    private var name: String = ""
    private var macAddress: String = ""
    private var status: String = ""
    private var dashboardName: String = ""
    private var roomno: String = ""

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getStatus(): String {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getDashboardName(): String {
        return dashboardName
    }

    fun setDashboardName(dashboardName: String) {
        this.dashboardName = dashboardName
    }

    fun getMacAddress(): String {
        return this.macAddress
    }

    fun setMacAddress(macAddress: String) {
        this.macAddress = macAddress
    }

    fun getRoomno(): String {
        return this.roomno
    }

    fun setRoomno(roomno: String) {
        this.roomno = roomno
    }
}


