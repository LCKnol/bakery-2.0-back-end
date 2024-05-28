package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Primary
@Component
class RoomMapperImp : RoomMapper {

    override fun mapRooms(resultSet: ResultSet): RoomCollectionDto {

        val roomCollection = RoomCollectionDto()
        val rooms = arrayListOf<RoomDto>()
        while (resultSet.next()) {
            val roomNo = resultSet.getString("roomNo")
            val roomDto = RoomDto()
            roomDto.setRoomNo(roomNo)
            rooms.add(roomDto)
        }
        roomCollection.setRooms(rooms)
        return roomCollection
    }

    override fun mapRoomsAndTeams(resultSet: ResultSet): RoomCollectionDto {
        val roomCollection = RoomCollectionDto()
        val roomsMap = mutableMapOf<String, RoomDto>()

        while (resultSet.next()) {
            val roomNo = resultSet.getString("roomNo")
            val teamName = resultSet.getString("teamname")
            val teamId = resultSet.getInt("teamid")

            // Check if the room exists in the map
            val roomDto = roomsMap.getOrPut(roomNo) { RoomDto().apply { setRoomNo(roomNo) } }

            // Check if teamName is not null
            if (teamName != null) {
                val teamDto = TeamDto().apply {
                    setName(teamName)
                    setId(teamId)
                }

                // Add the team to the room
                val teamCollectionDto = roomDto.getTeamCollection() ?: TeamCollectionDto().also {
                    roomDto.setTeamCollection(it)
                }
                teamCollectionDto.getTeamCollection().add(teamDto)
            }
        }

        // Convert the map values to a list and set it in the RoomCollectionDto
        roomCollection.setRooms(ArrayList(roomsMap.values))
        return roomCollection
    }


}