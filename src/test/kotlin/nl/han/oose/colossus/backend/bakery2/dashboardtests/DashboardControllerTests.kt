package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*



class DashboardControllerTests {


    private lateinit var sut: DashboardsController

    private lateinit var dashboardsService: DashboardsService

    private lateinit var  headerService: HeaderService

    private lateinit var userService: UserService


    @Test
    @BeforeEach
    fun setup() {
        sut = DashboardsController()
        dashboardsService = mock(DashboardsService::class.java)
        headerService = mock(HeaderService::class.java)
        userService = mock(UserService::class.java)
        sut.setDashboardsService(dashboardsService)
        sut.setTokenService(headerService)
        sut.setUserService(userService)
    }


    @Test
    fun testGetAllDashboardWorksCorrectly() {

        // Arrange
        val dashboard = DashboardCollectionDto()
        `when`(dashboardsService.getAllDashboards()).thenReturn(dashboard)
        // Act
        val response = sut.getAllDashboards().statusCode.value()
        // Assert
        assertEquals(200, response)
        verify(dashboardsService).getAllDashboards()
    }
    @Test
    fun testaddDashboardsCorrectly() {

        // Arrange
        val dashboard = DashboardDto(1,"test","test","test",1)
        // Act
        val response = sut.addDashboards(dashboard).statusCode.value()
        // Assert
        assertEquals(201, response)
        verify(dashboardsService).addDashboard(dashboard)
    }

    @Test
    fun testGetDashboardWorksCorrectly() {

        //Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", 1)
        `when`(dashboardsService.getDashboard(1)).thenReturn(dashboard)
        //Act
        val response = sut.getDashboard(1).statusCode.value()
        //Assert
        assertEquals(200, response)
        verify(dashboardsService).getDashboard(1)
    }

    @Test
    fun testEditDashboardWorksCorrectly() {

        //Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", 1)
        `when`(userService.getUserId(MockitoHelper.anyObject())).thenReturn(1)
        doNothing().`when`(dashboardsService).editDashboard(dashboard, 1)
        //Act
        val response = sut.editDashboard(dashboard).statusCode.value()
        //Assert
        assertEquals(200, response)
        verify(dashboardsService).editDashboard(dashboard, 1)
    }


}