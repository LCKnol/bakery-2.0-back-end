package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto

interface RoomService {
    fun getAllRooms(): RoomCollectionDto
    fun setRoomDao(roomDao: RoomDao)
}