package nl.han.oose.colossus.backend.bakery2.dto

class PiDto {
    private var id : Int = 0
    private var name: String = ""
    private var status: String = ""
    private var dashboardName: String = ""
    private var roomNo: Int = 0



    constructor(id: Int, name: String, status: String, dashboardName: String, roomNo: Int) {
        this.id = id
        this.status = status
        this.dashboardName = dashboardName
        this.roomNo = roomNo
    }
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

    fun getDisplay(): String {
        return dashboardName
    }

    fun setDisplay(display: String) {
        this.dashboardName = display
    }
    fun getRoomNo(): Int {
        return roomNo
    }

    fun setRoomNo(roomNo: Int) {
        this.roomNo = roomNo
    }

    fun getDashboardName(): String {
        return dashboardName
    }

    fun setDashboardName(dashboardName: String) {
        this.dashboardName = dashboardName
    }

}


