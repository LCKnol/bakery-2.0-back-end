package nl.han.oose.colossus.backend.bakery2.dashboardtests

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDaoImp
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*
import java.io.InputStreamReader

class DashboardDaoTests {

    private lateinit var sut: DashboardsDao

    private lateinit var dashboardsMapper: DashboardsMapper

    private var team: TeamDto = TeamDto()

    private lateinit var dbconnection: DatabaseConnection

    @BeforeEach
    fun setup() {
        sut = DashboardsDaoImp()
        dashboardsMapper = mock(DashboardsMapper::class.java)
        dbconnection = DatabaseConnection()
        val scriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setDashboardsMapper(dashboardsMapper)
        team.setId(1)
        team.setName("testTeam")
    }

    @Test
    fun testGetAllDashboardsCallWorksCorrectly() {
        // Arrange
        val dashboards = DashboardCollectionDto()
        `when`(dashboardsMapper.getAllDashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboards)

        //Act
        val result = sut.getAllDashboards(1)

        //Assert
        verify(dashboardsMapper).getAllDashboardsMapper(MockitoHelper.anyObject())
        assertEquals(dashboards, result)
    }

    @Test
    fun testAddDashboardWorksCorrectly() {
        // Arrange
<<<<<<< HEAD
        val dashboard = DashboardDto(12, "test", "uniek", 69, team, true)
=======
        val dashboard = DashboardDto(12, "test", "uniek", team, true)
>>>>>>> 887fdcd8c5f7a121baed5501652091394208b431

        // Act
        sut.addDashboard(dashboard)

        val statement =
            dbconnection.getConnection().prepareStatement("SELECT NAME FROM DASHBOARD WHERE NAME = 'uniek' ")
        val resultSet = statement.executeQuery()
        resultSet.next()
        val finalResult = resultSet.getString(1)

        // Assert
        assertEquals(dashboard.getDashboardName(), finalResult)
    }

    @Test
    fun testUpdateDashboardsCallWorksCorrectly() {
        // Arrange
        val dashboard: DashboardDto = DashboardDto(
            1,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "meme update",
<<<<<<< HEAD
            12,
=======
>>>>>>> 887fdcd8c5f7a121baed5501652091394208b431
            team,
            true
        )
        val statement =
            dbconnection.getConnection().prepareStatement("SELECT NAME FROM DASHBOARD WHERE DASHBOARDID = 1")

        // Act
        sut.editDashboard(dashboard)

        // Assert
        val resultSet = statement.executeQuery()
        resultSet.next()
        val result = resultSet.getString(1)

        assertEquals("meme update", result)
    }

    @Test
    fun testGetDashboardWorksCorrectly() {
        val expectedDashboard = DashboardDto(
            1,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "meme update",
<<<<<<< HEAD
            666,
=======
>>>>>>> 887fdcd8c5f7a121baed5501652091394208b431
            TeamDto(),
            true
        )
        `when`(dashboardsMapper.getDashboardMapper(MockitoHelper.anyObject())).thenReturn(expectedDashboard)

        //Act
        val result = sut.getDashboardForUser(1, 1)

        //Assert
        verify(dashboardsMapper).getDashboardMapper(MockitoHelper.anyObject())
        assertEquals(expectedDashboard, result)
    }

    @Test
    fun testDeleteDashboardsCallWorksCorrectly() {
        // Arrange
        val sampleDashboardId = 1

        val statement0 =
            dbconnection.getConnection().prepareStatement("UPDATE PI SET DASHBOARDID = NULL WHERE DASHBOARDID = ?")
        statement0.setInt(1, sampleDashboardId)

        val statement1 =
            dbconnection.getConnection().prepareStatement("SELECT DASHBOARDID FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement1.setInt(1, sampleDashboardId)

        val statement2 =
            dbconnection.getConnection().prepareStatement("SELECT DASHBOARDID FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement2.setInt(1, sampleDashboardId)

        // Act & Assert
        statement0.executeUpdate()


        // Dashboard with id sampleDashboardId should already be in the database
        val resultSet1 = statement1.executeQuery()

        assertDoesNotThrow { sut.deleteDashboard(sampleDashboardId) }

        val resultSet2 = statement2.executeQuery()

        assertTrue(resultSet1.next())

        // Dashboard with id sampleDashboardId should now be deleted
        assertFalse(resultSet2.next())
    }


    @Test
    fun testGetDashboardUrl() {
        //Arrange
        val expectedUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"

        //Act
        val result = sut.getDashboardUrl(1)

        //Assert
        assertEquals(expectedUrl, result)
    }
}