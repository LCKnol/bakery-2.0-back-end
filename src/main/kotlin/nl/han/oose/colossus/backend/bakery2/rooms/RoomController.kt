package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController {

    @Autowired
    private lateinit var roomService: RoomService

    fun getAllRooms(): RoomCollectionDto {
        //TODO
        return roomService.getAllRooms()
    }
}