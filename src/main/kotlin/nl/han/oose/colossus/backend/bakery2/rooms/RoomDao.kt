package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto

interface RoomDao {

    fun getAllRooms(): RoomCollectionDto
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setRoomMapper(mapper: RoomMapper)
    fun deleteRoom(roomNo: String)
}