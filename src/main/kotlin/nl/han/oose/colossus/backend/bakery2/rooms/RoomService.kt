package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto

interface RoomService {
    fun getAllRooms(): RoomCollectionDto
    fun setRoomDao(roomDao: RoomDao)
    fun deleteRoom(roomNo : String)

    fun addRoom(roomDto: RoomDto)
}