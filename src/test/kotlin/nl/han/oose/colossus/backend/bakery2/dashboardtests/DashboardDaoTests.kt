package nl.han.oose.colossus.backend.bakery2.dashboardtests

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDaoImp
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*
import java.io.InputStreamReader
import java.sql.ResultSet

class DashboardDaoTests {

    private lateinit var sut: DashboardsDao

    private lateinit var dashboardsMapper: DashboardsMapper

    private lateinit var resultSet: ResultSet

    private lateinit var dbconnection: DatabaseConnection

    @BeforeEach
    fun setup() {
        sut = DashboardsDaoImp()
        dashboardsMapper = mock(DashboardsMapper::class.java)
        dbconnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setDashboardsMapper(dashboardsMapper)
    }

    @Test
    fun testGetAllDashboardsCallWorksCorrectly() {
        // arrange
        val dashboard: DashboardCollectionDto = DashboardCollectionDto()
        `when`(dashboardsMapper.getAllDashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboard)

        // act
        val result = sut.getAllDashboards()

        //assert
        verify(dashboardsMapper).getAllDashboardsMapper(MockitoHelper.anyObject())
        Assertions.assertEquals(dashboard, result)
    }

    @Test
    fun testAddDashboardWorksCorrectly() {
        // arrange
        val dashboard: DashboardDto = DashboardDto(12, "test", "uniek", "test", 1)
        // act
        sut.addDashboard(dashboard)

        val statement =
            dbconnection.getConnection().prepareStatement("SELECT NAME FROM DASHBOARD WHERE NAME = 'uniek' ")
        val resultSet = statement.executeQuery()
        resultSet.next()
        val finalResult = resultSet.getString(1)

        //assert
        Assertions.assertEquals(dashboard.getName(), finalResult)
    }

    @Test
    fun testUpdateDashboardsCallWorksCorrectly() {
        // Arrange
        val dashboard: DashboardDto = DashboardDto(
            1,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "meme update",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images",
            1
        )
        val statement =
            dbconnection.getConnection().prepareStatement("SELECT NAME FROM DASHBOARD WHERE DASHBOARDID = 1")

        // Act
        sut.editDashboard(dashboard)

        // Assert
        val resultSet = statement.executeQuery()
        resultSet.next()
        val result = resultSet.getString(1)

        Assertions.assertEquals("meme update", result)
    }

    @Test
    fun testDeleteDashboardsCallWorksCorrectly() {
        // Arrange
        val sampleDashboardId = 1

        val statement0 =
            dbconnection.getConnection().prepareStatement("UPDATE PI SET DASHBOARDID = NULL WHERE DASHBOARDID = ?")
        statement0.setInt(1, sampleDashboardId)
        statement0.executeUpdate()

        // Act & Assert
        // Dashboard with id sampleDashboardId should already be in the database
        val statement1 =
            dbconnection.getConnection().prepareStatement("SELECT DASHBOARDID FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement1.setInt(1, sampleDashboardId)
        val resultSet1 = statement1.executeQuery()
        assertTrue(resultSet1.next())

        assertDoesNotThrow { sut.deleteDashboard(sampleDashboardId) }

        // Dashboard with id sampleDashboardId should now be deleted
        val statement2 =
            dbconnection.getConnection().prepareStatement("SELECT DASHBOARDID FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement2.setInt(1, sampleDashboardId)
        val resultSet2 = statement2.executeQuery()
        assertFalse(resultSet2.next())
    }
}