package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto

interface RoomDao {

    fun getAllRooms(): RoomCollectionDto
    fun getAllRoomsAndTeams(): RoomCollectionDto
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setRoomMapper(mapper: RoomMapper)
    fun deleteRoom(roomNo: String)
    fun removeTeamFromRoom(roomNo: String, team: Int)
    fun removePisFromRoom(roomNo: String)
    fun addTeamToRoom(roomNo: String, team: Int)
    fun addRoom(roomDto: RoomDto)
}