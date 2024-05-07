package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
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
}