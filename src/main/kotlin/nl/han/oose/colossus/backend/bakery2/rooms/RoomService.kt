package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto

interface RoomService {
    fun getAllRooms(): RoomCollectionDto
    fun getAllRoomsAndTeams(): RoomCollectionDto
    fun setRoomDao(roomDao: RoomDao)
    fun deleteRoom(roomNo : String)
    fun removeTeamFromRoom(roomNo: String, team: Int)
    fun addTeamToRoom(roomNo: String, team: Int)
    fun addRoom(roomDto: RoomDto)


}