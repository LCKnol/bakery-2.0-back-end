package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.rooms.RoomDao
import nl.han.oose.colossus.backend.bakery2.rooms.RoomService
import nl.han.oose.colossus.backend.bakery2.rooms.RoomServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class TeamServiceTests {
    private lateinit var sut: TeamService

    private lateinit var teamDao: TeamDao

    @BeforeEach
    fun setup() {
        sut = TeamServiceImp()
        teamDao = mock(TeamDao::class.java)
        sut.setTeamDao(teamDao)
    }

    @Test
    fun getTeamsFromUserPassesToDao() {
        // Arrange
        val teams = TeamCollectionDto()
        `when`(teamDao.getTeams(1)).thenReturn(teams)

        // Act
        sut.getTeamsFromUser(1)

        // Assert
        verify(teamDao).getTeams(1)
        
    }
}