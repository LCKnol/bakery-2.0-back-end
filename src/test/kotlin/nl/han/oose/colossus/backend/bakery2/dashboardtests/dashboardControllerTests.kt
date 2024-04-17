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
    fun TestGetAllPlaylistResponse200() {
        // Arrange
        // Act
        val response = sut.getAllDashboards("test token").statusCode.value()
        // Assert
        assertEquals(200, response)
    }


    @Test
    fun TestGetAllDashboardsCallsNextServiceFunction() {
        // Arrange
        `when`(dashboardsService.getAllDashboards()).thenReturn(DashboardCollectionDto())
        // Act
        sut.getAllDashboards("test token")

        // Assert
        verify(dashboardsService).getAllDashboards()
    }



}