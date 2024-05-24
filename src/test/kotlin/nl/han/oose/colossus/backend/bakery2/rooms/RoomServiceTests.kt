package nl.han.oose.colossus.backend.bakery2.rooms

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.RoomDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*

class RoomServiceTests {

    private lateinit var sut: RoomServiceImp
    private lateinit var roomDao: RoomDao

    @BeforeEach
    fun setup() {
        roomDao = mock(RoomDao::class.java)
        sut = RoomServiceImp()
        sut.setRoomDao(roomDao)
    }

    @Test
    fun testGetAllRooms() {
        // Arrange
        val expectedRooms = RoomCollectionDto()
        `when`(roomDao.getAllRooms()).thenReturn(expectedRooms)

        // Act
        val result = sut.getAllRooms()

        // Assert
        assertEquals(expectedRooms, result)
        verify(roomDao).getAllRooms()
    }

    @Test
    fun testGetAllRoomsAndTeams() {
        // Arrange
        val expectedRooms = RoomCollectionDto()
        `when`(roomDao.getAllRoomsAndTeams()).thenReturn(expectedRooms)

        // Act
        val result = sut.getAllRoomsAndTeams()

        // Assert
        assertEquals(expectedRooms, result)
        verify(roomDao).getAllRoomsAndTeams()
    }

    @Test
    fun testDeleteRoom() {
        // Arrange
        val roomNo = "13.01"

        // Act
        assertDoesNotThrow { sut.deleteRoom(roomNo) }

        // Assert
        verify(roomDao).removePisFromRoom(roomNo)
        verify(roomDao).deleteRoom(roomNo)
    }

    @Test
    fun testRemoveTeamFromRoom() {
        // Arrange
        val roomNo = "13.01"
        val teamId = 1

        // Act
        assertDoesNotThrow { sut.removeTeamFromRoom(roomNo, teamId) }

        // Assert
        verify(roomDao).removeTeamFromRoom(roomNo, teamId)
    }

    @Test
    fun testAddTeamToRoom() {
        // Arrange
        val roomNo = "13.01"
        val teamId = 1

        // Act
        assertDoesNotThrow { sut.addTeamToRoom(roomNo, teamId) }

        // Assert
        verify(roomDao).addTeamToRoom(roomNo, teamId)
    }

    @Test
    fun testAddRoom() {
        // Arrange
        val roomNo = "11:11"
        val roomDto = RoomDto().apply { setRoomNo(roomNo) }

        // Act
        assertDoesNotThrow { sut.addRoom(roomDto) }

        // Assert
        verify(roomDao).addRoom(roomDto)
    }
}