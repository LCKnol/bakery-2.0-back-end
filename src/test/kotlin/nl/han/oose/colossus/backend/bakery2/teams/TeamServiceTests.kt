package nl.han.oose.colossus.backend.bakery2.teams

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.*
import nl.han.oose.colossus.backend.bakery2.rooms.RoomDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class TeamServiceTests {
    private lateinit var sut: TeamService

    private lateinit var teamDao: TeamDao
    private lateinit var roomDao: RoomDao

    @BeforeEach
    fun setup() {

        sut = TeamServiceImp()
        teamDao = mock(TeamDao::class.java)
        roomDao = mock(RoomDao::class.java)
        sut.setTeamDao(teamDao)
        sut.setRoomDao(roomDao)
    }

    @Test
    fun getTeamsFromUserPassesToDao() {
        // Arrange
        val teams = TeamCollectionDto()
        `when`(teamDao.getTeamsFromUser(1)).thenReturn(teams)

        // Act
        var response =  sut.getTeamsFromUser(1)

        // Assert
        verify(teamDao).getTeamsFromUser(1)
        Assertions.assertEquals(response,teams)

    }

    @Test
    fun testGetAllTeamsCorectly() {
        // Arrange
        val teams = TeamCollectionDto()
        `when`(teamDao.getAllTeams()).thenReturn(teams)

        // Act
        var response =  sut.getAllTeams()

        // Assert
        verify(teamDao).getAllTeams()
        Assertions.assertEquals(response,teams)

    }

    @Test
    fun assignUsertoTeam() {
        //Arrange
        var userId = 1
        var teamId = 1
        //Act
        sut.assignUserToTeam(userId,teamId)
        //Assert

        verify(teamDao).assignUserToTeam(userId,teamId)

    }

    @Test
    fun removeUserFromTeam() {
        //Arrange
        var userId = 1
        var teamId = 1

        //Act
        sut.removeUserFromTeam(userId,teamId)

        //Assert
        verify(teamDao).removeUserFromTeam(userId,teamId)
    }

    @Test
    fun testGetTeamsNotInRoom() {
        // Arrange
        val roomNo = "12.01"
        val teams = TeamCollectionDto()
        `when`(teamDao.getTeamsNotInRoom(roomNo)).thenReturn(teams)

        // Act
        val response = sut.getTeamsNotInRoom(roomNo)

        // Assert
        verify(teamDao).getTeamsNotInRoom(roomNo)
        assertEquals(teams, response)
    }

    @Test
    fun addTeamTest() {
        // Arrange
        val teamInfoDto = TeamInfoDto().apply {
            setName("Test Team")
            setMembers(listOf(UserDto(1, "reem", "whatever", "reem@whatever.com", "password", ArrayList(), false)))
            setRooms(listOf(RoomDto().apply { setRoomNo("101") }))
        }

        val teamDto = TeamDto().apply {
            setId(1)
            setName("Test Team")
        }

        `when`(teamDao.getTeam("Test Team")).thenReturn(teamDto)

        // Act
        sut.addTeam(teamInfoDto)

        // Assert
        verify(teamDao).addTeam(teamInfoDto)
        verify(teamDao).getTeam("Test Team")
        verify(teamDao).assignUserToTeam(1, 1)
        verify(roomDao).addTeamToRoom("101", 1)
    }

    @Test
    fun removeTeamCorrectly() {
        // Arrange
        val teamId = 1

        // Act
        sut.removeTeam(teamId)

        // Assert
        verify(teamDao).removeTeam(teamId)
    }

    @Test
    fun getAllTeamInfoCorrectly() {
        // Arrange
        val teamInfoCollection = TeamInfoCollectionDto()
        `when`(teamDao.getAllTeamInfo()).thenReturn(teamInfoCollection)

        // Act
        val response = sut.getAllTeamInfo()

        // Assert
        verify(teamDao).getAllTeamInfo()
        assertEquals(response, teamInfoCollection)
    }
}