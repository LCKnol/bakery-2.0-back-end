package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import java.sql.ResultSet

interface RoomMapper {
    fun mapRooms(resultSet: ResultSet): RoomCollectionDto

    fun mapRoomsAndTeams(resultSet: ResultSet): RoomCollectionDto
}