package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import kotlin.jvm.Throws


@Primary
@Component
class RoomDaoImp : RoomDao {

    @Autowired
    private lateinit var roomMapper: RoomMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setRoomMapper(mapper: RoomMapper) {
        roomMapper = mapper
    }

    @Throws(ServerErrorException::class)
    override fun getAllRooms(): RoomCollectionDto {
        val connection = dbConnection.getConnection()
        val statement = connection.prepareStatement("SELECT roomNo FROM ROOM")
        val result = statement.executeQuery()
        val rooms = roomMapper.mapRooms(result)
        statement.close()
        connection.close()
        return rooms

    }
}