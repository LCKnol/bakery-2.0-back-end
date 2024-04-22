package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*



class dashboardControllerTests {


    private lateinit var sut: DashboardsController

    private lateinit var dashboardsService: DashboardsService


    @Test
    @BeforeEach
    fun setup() {
        sut = DashboardsController()
        dashboardsService = mock(DashboardsService::class.java)
        sut.setDashboardsService(dashboardsService)
    }


    @Test
    fun TestGetAllDashboardWorksCorrectly() {

        // Arrange
        val dashboard: DashboardCollectionDto = DashboardCollectionDto()
        `when`(dashboardsService.getAllDashboards()).thenReturn(dashboard)
        // Act
        val response = sut.getAllDashboards().statusCode.value()
        // Assert
        assertEquals(200, response)
        verify(dashboardsService).getAllDashboards()
    }
    @Test
    fun TestaddDashboardsCorrectly() {

        // Arrange
        val dashboard: DashboardDto = DashboardDto(1,"test","test","test",1)
        // Act
        val response = sut.addDashboards(dashboard).statusCode.value()
        // Assert
        assertEquals(201, response)
        verify(dashboardsService).addDashboard(dashboard)
    }

    @Test
    fun testUpdateDashboardWorksCorrectly() {
        // Arrange
        val dashboard: DashboardDto = DashboardDto(1, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "meme", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", 1)
        doNothing().`when`(dashboardsService).editDashboard(dashboard,1)

        // Act
        val result = sut.editDashboard(dashboard).statusCode

        // Assert
        verify(dashboardsService).editDashboard(dashboard,1)
        Assertions.assertEquals(204, result.value())
    }

}