package nl.han.oose.colossus.backend.bakery2.dto

class UserInfoDto {
    private var firstname: String = ""
    private var lastname: String = ""
    private var _teams: ArrayList<TeamDto> = ArrayList()
    private var isAdmin: Boolean = false
    val teams: List<TeamDto>
        get() = _teams

    private var _rooms: ArrayList<RoomDto> = ArrayList()
    val rooms: List<RoomDto>
        get() = _rooms

    fun getFirstname(): String {
        return firstname
    }
    fun setIsAdmin(isAdmin: Boolean) {
        this.isAdmin = isAdmin
    }

    fun getIsAdmin(): Boolean {
        return isAdmin
    }

    fun setFirstname(firstname: String) {
        this.firstname = firstname
    }

    fun getLastname(): String {
        return lastname
    }

    fun setLastname(lastname: String) {
        this.lastname = lastname
    }

    fun setTeams(newTeams: List<TeamDto>) {
        _teams.clear()
        _teams.addAll(newTeams)
    }

    fun setRooms(newRooms: List<RoomDto>) {
        _rooms.clear()
        _rooms.addAll(newRooms)
    }
}
