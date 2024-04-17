package nl.han.oose.colossus.backend.bakery2.dto

import nl.han.oose.colossus.backend.bakery2.dto.RoomDTO
import nl.han.oose.colossus.backend.bakery2.dto.TeamDTO

class UserInfoDTO {
    private var firstname: String = ""
    private var lastname: String = ""
    private var _teams: ArrayList<TeamDTO> = ArrayList()
    val teams: List<TeamDTO>
        get() = _teams

    private var _rooms: ArrayList<RoomDTO> = ArrayList()
    val rooms: List<RoomDTO>
        get() = _rooms

    fun getFirstname(): String {
        return firstname
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

    fun setTeams(newTeams: List<TeamDTO>) {
        _teams.clear()
        _teams.addAll(newTeams)
    }

    fun setRooms(newRooms: List<RoomDTO>) {
        _rooms.clear()
        _rooms.addAll(newRooms)
    }
}
