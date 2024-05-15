package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
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
        val result = sut.getTeams(userId)

        // Assert
        Assertions.assertEquals(result, teamCollectionDto)
    }
}