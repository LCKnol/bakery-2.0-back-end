package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
        `when`(teamDao.getTeamsFromUser(1)).thenReturn(teams)

        // Act
        sut.getTeamsFromUser(1)

        // Assert
        verify(teamDao).getTeamsFromUser(1)

    }
}