package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import junit.framework.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TeamControllerTests {

    private lateinit var sut: TeamController

    private lateinit var teamService: TeamService

    private lateinit var headerService: HeaderService

    @BeforeEach
    fun setup() {
        sut = TeamController()
        teamService = mock(TeamService::class.java)
        headerService = mock(HeaderService::class.java)
        sut.setTeamService(teamService)
        sut.setHeaderService(headerService)
    }

    @Test
    fun getTeamsFromUserCorrectly() {
        //Arrange
        val teams = TeamCollectionDto()
        Mockito.`when`(teamService.getTeamsFromUser(0)).thenReturn(teams)
        //Act
        val response = sut.getTeamsFromUser()
        //Assert
        assertEquals(200, response.statusCode.value())
        verify(teamService).getTeamsFromUser(0)
        assertEquals(response.body, teams)
    }
}