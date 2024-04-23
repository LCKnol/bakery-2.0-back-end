package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class DashboardControllerTests {

    private lateinit var sut: DashboardsController

    private lateinit var dashboardsService: DashboardsService

    private lateinit var tokenService: TokenService

    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        sut = DashboardsController()
        dashboardsService = mock(DashboardsService::class.java)
        tokenService = mock(TokenService::class.java)
        userService = mock(UserService::class.java)
        sut.setDashboardsService(dashboardsService)
        sut.setTokenService(tokenService)
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

    @Test
    fun testAddDashboardsCorrectly() {
        // Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", 1)
        // Act
        val response = sut.addDashboards(dashboard).statusCode.value()
        // Assert
        assertEquals(201, response)
        verify(dashboardsService).addDashboard(dashboard)
    }

    @Test
    fun testDeleteDashboardsCorrectly() {
        // Arrange
        val mockToken = "9e8b4196-b691-45f9-8fb7-acec4f0f9a4b"
        val dashboardId = 2
        val mockUserId = 1

        `when`(tokenService.getToken()).thenReturn(mockToken)
        `when`(userService.getUserId(mockToken)).thenReturn(mockUserId)

        // Act
        val response = sut.deleteDashboard(dashboardId).statusCode

        // Assert
        assertEquals(HttpStatus.OK, response)

        verify(tokenService).getToken()
        verify(userService).getUserId(mockToken)
        verify(dashboardsService).deleteDashboard(dashboardId, mockUserId)
    }
}