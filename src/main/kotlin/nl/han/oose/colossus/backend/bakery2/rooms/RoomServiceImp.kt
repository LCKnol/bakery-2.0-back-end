package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RoomServiceImp : RoomService {

    @Autowired
    private lateinit var roomDao: RoomDao

    override fun setRoomDao(roomDao: RoomDao) {
        this.roomDao = roomDao
    }


    override fun getAllRooms(): RoomCollectionDto {
        return roomDao.getAllRooms()
    }
}