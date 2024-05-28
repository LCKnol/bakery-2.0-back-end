package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
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

    override fun deleteRoom(roomNo: String) {
        roomDao.removePisFromRoom(roomNo)
        roomDao.deleteRoom(roomNo)
    }

    override fun removeTeamFromRoom(roomNo: String, team: Int) {
        roomDao.removeTeamFromRoom(roomNo, team)
    }

    override fun addTeamToRoom(roomNo: String, team: Int) {
        roomDao.addTeamToRoom(roomNo, team)
    }

    override fun addRoom(roomDto: RoomDto) {
        roomDao.addRoom(roomDto)
    }

    override fun getAllRooms(): RoomCollectionDto {
        return roomDao.getAllRooms()
    }

    override fun getAllRoomsAndTeams(): RoomCollectionDto {
       return roomDao.getAllRoomsAndTeams()
    }
}