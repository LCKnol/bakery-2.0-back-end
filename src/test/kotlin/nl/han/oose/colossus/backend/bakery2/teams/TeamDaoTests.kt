package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.InputStreamReader

class TeamDaoTests {
    private lateinit var sut: TeamDao
    private lateinit var teamMapper: TeamMapper
    private lateinit var dbConnection: DatabaseConnection

    @BeforeEach
    fun setUp() {
        sut = TeamDaoImp()
        teamMapper = mock(TeamMapper::class.java)
        dbConnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbConnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))

        sut.setDatabaseConnection(dbConnection)
        sut.setTeamMapper(teamMapper)
    }

    @Test
    fun getAllTeamsFromUser() {
        // Arrange
        val userId = 1
        val teamCollectionDto = TeamCollectionDto()
        `when`(teamMapper.mapUserTeams(MockitoHelper.anyObject())).thenReturn(teamCollectionDto)

        // Act
        val result = sut.getTeamsFromUser(userId)

        // Assert
        verify(teamMapper).mapUserTeams(MockitoHelper.anyObject())
        Assertions.assertEquals(result, teamCollectionDto)
    }



    @Test
    fun testGetAllTeamsCorectly() {
        // Arrange
        val teamCollectionDto = TeamCollectionDto()
        `when`(teamMapper.mapUserTeams(MockitoHelper.anyObject())).thenReturn(teamCollectionDto)

        // Act
        val result = sut.getAllTeams()

        // Assert
        verify(teamMapper).mapUserTeams(MockitoHelper.anyObject())
        Assertions.assertEquals(result, teamCollectionDto)

    }

    @Test
    fun assignUsertoTeam() {
        //Arrange
        var userId = 1
        var teamId = 3
        dbConnection = DatabaseConnection()
        val statement = dbConnection.getConnection().prepareStatement("SELECT * FROM USERINTEAM WHERE USERID = ? AND TEAMID = ?")
        statement.setInt(1,userId)
        statement.setInt(2,teamId)
        // Act
        sut.assignUserToTeam(userId,teamId)
        statement.executeQuery()
        var result = statement.resultSet.first()

        // Assert
        Assertions.assertEquals(true, result)

    }

    @Test
    fun removeUserFromTeam() {
        //Arrange
        var userId = 1
        var teamId = 1
        dbConnection = DatabaseConnection()
        val statement = dbConnection.getConnection().prepareStatement("SELECT * FROM USERINTEAM WHERE USERID = ? AND TEAMID = ?")
        statement.setInt(1,userId)
        statement.setInt(2,teamId)
        // Act
        sut.removeUserFromTeam(userId,teamId)
        statement.executeQuery()
        var result = statement.resultSet.first()

        // Assert
        Assertions.assertEquals(false, result)
    }

    @Test
    fun testGetTeamsNotInRoomWorksCorrectly() {
        // Arrange
        val roomNo = "13.01"
        val teamCollectionDto = TeamCollectionDto()
        `when`(teamMapper.mapUserTeams(MockitoHelper.anyObject())).thenReturn(teamCollectionDto)

        // Act
        val result = sut.getTeamsNotInRoom(roomNo)

        // Assert
        verify(teamMapper).mapUserTeams(MockitoHelper.anyObject())
        Assertions.assertEquals(result, teamCollectionDto)
    }
}