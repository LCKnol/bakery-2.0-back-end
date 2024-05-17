package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException


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
    override fun deleteRoom(roomNo: String) {
            val query = "DELETE FROM TEAMINROOM WHERE ROOMNO = ?"
            val query2 = "DELETE FROM ROOM WHERE ROOMNO = ?"

            val connection = dbConnection.getConnection()
            val statement = connection.prepareStatement(query)
                statement.setString(1, roomNo)
                statement.executeUpdate()
                statement.close()
            val statement2 = connection.prepareStatement(query2)
                statement2.setString(1, roomNo)
                statement2.executeUpdate()
                statement2.close()
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