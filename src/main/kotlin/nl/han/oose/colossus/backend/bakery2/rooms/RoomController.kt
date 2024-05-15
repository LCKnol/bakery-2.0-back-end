package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rooms")
class RoomController {

    @Autowired
    private lateinit var roomService: RoomService

    fun setRoomService(roomService: RoomService) {
        this.roomService = roomService
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getAllRooms(): ResponseEntity<RoomCollectionDto> {
        return ResponseEntity(roomService.getAllRooms(), HttpStatus.OK)
    }

    @DeleteMapping(path = ["/{roomNo}"])
    @Authenticate
    fun deleteRoom(@PathVariable roomNo: String): ResponseEntity<HttpStatus> {
        roomService.deleteRoom(roomNo)
        return ResponseEntity(HttpStatus.OK)
    }
}