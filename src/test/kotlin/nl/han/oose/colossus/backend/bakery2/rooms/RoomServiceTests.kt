package nl.han.oose.colossus.backend.bakery2.rooms

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
}