package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.InputStreamReader
import java.sql.ResultSet

class RoomDaoTests {

    private lateinit var sut: RoomDao

    private lateinit var resultSet: ResultSet

    private lateinit var roomMapper: RoomMapper

    private lateinit var dbconnection: DatabaseConnection

    @BeforeEach
    fun setup() {
        sut = RoomDaoImp()
        roomMapper = mock(RoomMapper::class.java)
        dbconnection = DatabaseConnection()
        val scriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setRoomMapper(roomMapper)
    }

    @Test
    fun testGetAllRoomsWorksCorrectly() {
        // arrange
        val room: RoomCollectionDto = RoomCollectionDto()
        Mockito.`when`(roomMapper.mapRooms(MockitoHelper.anyObject())).thenReturn(room)

        // act
        val result = sut.getAllRooms()

        //assert
        Mockito.verify(roomMapper).mapRooms(MockitoHelper.anyObject())
        Assertions.assertEquals(room, result)
    }
}