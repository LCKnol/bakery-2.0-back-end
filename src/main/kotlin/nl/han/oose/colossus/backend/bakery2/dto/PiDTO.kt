package nl.han.oose.colossus.backend.bakery2.dto

class PiDTO {
    private var name: String = ""
    private var status: String = ""
    private var display: String = ""

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
        return display
    }

    fun setDisplay(display: String) {
        this.display = display
    }
}


