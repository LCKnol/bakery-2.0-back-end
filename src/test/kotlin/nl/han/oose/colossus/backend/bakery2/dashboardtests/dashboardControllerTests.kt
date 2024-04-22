package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*



class dashboardControllerTests {


    private lateinit var sut: DashboardsController

    private lateinit var dashboardsService: DashboardsService

    private lateinit var  tokenService: TokenService

    private lateinit var userService: UserService


    @Test
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


}