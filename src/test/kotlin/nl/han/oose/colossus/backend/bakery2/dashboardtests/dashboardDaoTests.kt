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
import org.mockito.Mockito.*
import java.io.InputStreamReader
import java.sql.ResultSet

class dashboardDaoTests {


    private lateinit var  sut: DashboardsDao

    private lateinit var dashboardsMapper:DashboardsMapper

    private lateinit var resultSet: ResultSet




    @Test
    @BeforeEach
    fun setup() {
        sut = DashboardsDaoImp()
        dashboardsMapper = mock(DashboardsMapper::class.java)
        val dbconnection: DatabaseConnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setDashboardsMapper(dashboardsMapper)
        
    }

    @Test
    fun TestGetAllDashboardsCallWorksCorrectly() {

        // arrange
        val dashboard: DashboardCollectionDto = DashboardCollectionDto()
        `when`(dashboardsMapper.getAlldashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboard)

        // act
        val result = sut.getAllDashboards()

        //assert
        verify(dashboardsMapper).getAlldashboardsMapper(MockitoHelper.anyObject())
        Assertions.assertEquals(dashboard, result)
    }

    //    @Test
//    fun TestAdddashboardWorksCorrectly() {
//
//        // arrange
//        val dashboard: DashboardDto = DashboardDto(12,"test","test","test",1)
//
//        `when`(dashboardsMapper.getAlldashboardsMapper(MockitoHelper.anyObject())).thenReturn(dashboard)
//        val statement = dbconnection.getConnection().prepareStatement("SELECT * FROM DASHBOARD")
//        this.resultSet = statement.executeQuery()
//        // act
//        val result = sut.getAllDashboards()
//
//        //assert
//        verify(dashboardsMapper).getAlldashboardsMapper(MockitoHelper.anyObject())
//        Assertions.assertEquals(dashboard, result)
//    }


    @Test
    fun testUpdateDashboardsCallWorksCorrectly() {
        // Arrange
        val dashboard: DashboardDto = DashboardDto(1, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "meme update", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", 1)
        val statement = conn.prepareStatement("SELECT NAME FROM DASHBOARD WHERE DASHBOARDID = 1")

        // Act
        sut.editDashboard(dashboard)

        // Assert
        val resultSet = statement.executeQuery()
        resultSet.next()
        val result = resultSet.getString(1)

        Assertions.assertEquals("meme update", result)



    }

}