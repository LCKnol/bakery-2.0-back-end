package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RoomServiceImp : RoomService {

    override fun getAllRooms(): RoomCollectionDto {
        //TODO
        return RoomCollectionDto()
    }

}