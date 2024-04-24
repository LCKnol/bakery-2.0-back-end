package nl.han.oose.colossus.backend.bakery2.dto

class PiDto {
    private var id : Int = 0
    private var name: String = ""
    private var status: String = ""
    private var dashboardName: String = ""

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
}


