package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

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
        val response = sut.getTeamsFromCurrentUser()
        //Assert
        assertEquals(200, response.statusCode.value())
        verify(teamService).getTeamsFromUser(0)
        assertEquals(response.body, teams)
    }

    @Test
    fun testGetAllTeamsCorectly() {
        //Arrange
        val teams = TeamCollectionDto()
        Mockito.`when`(teamService.getAllTeams()).thenReturn(teams)
        //Act
        val response = sut.getAllTeams()
        //Assert
        assertEquals(200, response.statusCode.value())
        verify(teamService).getAllTeams()
        assertEquals(response.body, teams)
    }

    @Test
    fun assignUsertoTeam() {
        //Arrange
        var userId = 1
        var teamId = 1
        //Act
        var response = sut.assignUsertoTeam(userId,teamId)

        //Assert
        verify(teamService).assignUserToTeam(userId,teamId)
        assertEquals(HttpStatus.OK,response.statusCode)

    }

    @Test
    fun removeUserFromTeam() {
        //Arrange
        var userId = 1
        var teamId = 1
        //Act
        var response = sut.removeUserFromTeam(userId,teamId)

        //Assert
        verify(teamService).removeUserFromTeam(userId,teamId)
        assertEquals(HttpStatus.OK,response.statusCode)
    }
}