package nl.han.oose.colossus.backend.bakery2.rooms

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*

class RoomServiceTests {

    private lateinit var sut: RoomService

    private lateinit var roomDao: RoomDao

    @BeforeEach
    fun setup() {
        sut = RoomServiceImp()
        roomDao = mock(RoomDao::class.java)
        sut.setRoomDao(roomDao)
    }

    @Test
    fun testGetAllRoomsCallsNextDaoFunction() {
        //Arrange
        val expectedRooms = RoomCollectionDto()
        `when`(roomDao.getAllRooms()).thenReturn(expectedRooms)
        //Act
        val result = sut.getAllRooms()
        //Assert
        assertEquals(expectedRooms, result)
        verify(roomDao).getAllRooms()
    }
    @Test
    fun testDeleteRoomCallsNextDaoFunction() {
        // Arrange
        val roomNo = "13.01"

        // Act & Assert
        assertDoesNotThrow { sut.deleteRoom(roomNo) }

        verify(roomDao).deleteRoom(roomNo)
    }

    @Test
    fun testAddDashboardsCallsNextDaoFunction() {
        // Arrange
        val roomNo = "11:11"
        val roomDto = RoomDto()
        roomDto.setRoomNo(roomNo)
        // Act
        sut.addRoom(roomDto)

        // Assert
        verify(roomDao).addRoom(roomDto)
    }
}