package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
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

}