package nl.han.oose.colossus.backend.bakery2.rooms

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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

    @Test
    fun testDeleteRoomCallWorksCorrectly() {
        // Arrange
        val roomNo = "01.01"

        val statement0 =
                dbconnection.getConnection().prepareStatement("INSERT INTO ROOM (ROOMNO) VALUES (?)")
        statement0.setString(1,roomNo)

        val statement1 =
                dbconnection.getConnection().prepareStatement("SELECT ROOMNO FROM ROOM WHERE ROOMNO = ?")
        statement1.setString(1,roomNo)

        val statement2 =
                dbconnection.getConnection().prepareStatement("SELECT ROOMNO FROM ROOM WHERE ROOMNO = ?")
        statement2.setString(1,roomNo)

        // Act & Assert
        statement0.execute()


        // Dashboard with id sampleDashboardId should already be in the database
        val resultSet1 = statement1.executeQuery()

        assertDoesNotThrow { sut.deleteRoom(roomNo) }

        val resultSet2 = statement2.executeQuery()

        Assertions.assertTrue(resultSet1.next())

        Assertions.assertFalse(resultSet2.next())
    }

    @Test
    fun testAddRoomWorksCorrectly() {
        // Arrange
        val roomNo = "11:11"
        val roomDto = RoomDto()
        roomDto.setRoomNo(roomNo)
        // Act
        sut.addRoom(roomDto)

        val statement = dbconnection.getConnection().prepareStatement("SELECT ROOMNO FROM ROOM WHERE ROOMNO = ? ")
        statement.setString(1, roomDto.getRoomNo())
        val resultSet = statement.executeQuery()
        resultSet.next()
        val finalResult = resultSet.getString(1)

        // Assert
        Assertions.assertEquals(roomDto.getRoomNo(), finalResult)
    }
}