package nl.han.oose.colossus.backend.bakery2.rooms

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
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
        // Arrange
        val rooms = RoomCollectionDto()
        Mockito.`when`(roomService.getAllRooms()).thenReturn(rooms)

        // Act
        val response = sut.getAllRooms()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(roomService).getAllRooms()
    }

    @Test
    fun testGetAllRoomsAndTeamsWorksCorrectly() {
        // Arrange
        val rooms = RoomCollectionDto()
        Mockito.`when`(roomService.getAllRoomsAndTeams()).thenReturn(rooms)

        // Act
        val response = sut.getAllRoomsAndTeams()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(roomService).getAllRoomsAndTeams()
    }

    @Test
    fun testDeleteRoomWorksCorrectly() {
        // Arrange
        val roomNo = "13.01"

        // Act
        val response = sut.deleteRoom(roomNo)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(roomService).deleteRoom(roomNo)
    }

    @Test
    fun testAddRoomWorksCorrectly() {
        // Arrange
        val roomNo = "11:11"
        val roomDto = RoomDto().apply { setRoomNo(roomNo) }

        // Act
        val response = sut.addRoom(roomDto)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        verify(roomService).addRoom(roomDto)
    }

    @Test
    fun testRemoveUserFromTeamWorksCorrectly() {
        // Arrange
        val roomNo = "13.01"
        val teamId = 1

        // Act
        val response = sut.removeUserFromTeam(roomNo, teamId)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(roomService).removeTeamFromRoom(roomNo, teamId)
    }

    @Test
    fun testAssignUserToTeamWorksCorrectly() {
        // Arrange
        val roomNo = "13.01"
        val teamId = 1

        // Act
        val response = sut.assignUsertoTeam(roomNo, teamId)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(roomService).addTeamToRoom(roomNo, teamId)
    }
}
