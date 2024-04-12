package nl.han.oose.colossus.backend.bakery2.dto

class UserInfoDTO {
    private var name: String = ""
    private var _teams: ArrayList<TeamDTO> = ArrayList()
    val teams: List<TeamDTO>
        get() = _teams

    private var _rooms: ArrayList<RoomDTO> = ArrayList()
    val rooms: List<RoomDTO>
        get() = _rooms

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setTeams(newTeams: List<TeamDTO>) {
        _teams.clear()
        _teams.addAll(newTeams)
    }

    fun setRooms(newRooms: List<RoomDTO>) {
        _rooms.clear()
        _rooms.addAll(newRooms)
    }
}
