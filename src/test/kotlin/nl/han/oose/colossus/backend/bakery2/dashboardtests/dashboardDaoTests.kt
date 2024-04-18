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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.ResultSet

class dashboardDaoTests {


    private lateinit var  sut: DashboardsDao

    private var connection: Connection? = null

    private lateinit var dashboardsMapper:DashboardsMapper

    private lateinit var resultSet: ResultSet




    @Test
    @BeforeEach
    fun setup() {
        sut = DashboardsDaoImp()
        dashboardsMapper = mock(DashboardsMapper::class.java)
        val dbconnection: DatabaseConnection = DatabaseConnection()
        val scriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")))
        sut.setDatabaseConnection(dbconnection)
        sut.setDashboardsMapper(dashboardsMapper)

        val statement = dbconnection.getConnection().prepareStatement("SELECT * FROM DASHBOARD")
        this.resultSet = statement.executeQuery()
    }

    @Test
    fun TestGetAllDashboardsCallWorksCorrectly() {

        // arrange
        val dashboard: DashboardCollectionDto = DashboardCollectionDto()
        `when`(dashboardsMapper.getAlldashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboard)

        // act
        val result = sut.getAllDashboards()
        Assertions.assertEquals(dashboard, result)
    }

    @Test
    fun TestGetAllDashboardsCallsMapper() {

        // arrange
        val dashboard: DashboardCollectionDto = DashboardCollectionDto()
        `when`(dashboardsMapper.getAlldashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboard)

        // act
        val result = sut.getAllDashboards()
        verify(dashboardsMapper).getAlldashboardsMapper(MockitoHelper.anyObject())
    }
}