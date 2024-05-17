package nl.han.oose.colossus.backend.bakery2.rooms


import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus

class RoomControllerTests {

    private lateinit var sut: RoomController

    private lateinit var roomService: RoomService

    @BeforeEach
    fun setup() {
        sut = RoomController()
        roomService = mock(RoomService::class.java)
        sut.setRoomService(roomService)
    }

    @Test
    fun testGetAllRoomsWorksCorrectly() {
        //Arrange
        val rooms = RoomCollectionDto()
        Mockito.`when`(roomService.getAllRooms()).thenReturn(rooms)
        //Act
        val response = sut.getAllRooms().statusCode.value()
        //Assert
        assertEquals(200, response)
        verify(roomService).getAllRooms()
    }
    @Test
    fun testDeleteRoomWorksCorrectly() {
        // Arrange
        val roomNo = "13.01"

        // Act
        val response = sut.deleteRoom(roomNo).statusCode

        // Assert
        assertEquals(HttpStatus.OK, response)

        verify(roomService).deleteRoom(roomNo)
    }
}