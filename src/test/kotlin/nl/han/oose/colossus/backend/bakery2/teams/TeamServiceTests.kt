package nl.han.oose.colossus.backend.bakery2.teams

import junit.framework.Assert
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserCollectionDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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
}