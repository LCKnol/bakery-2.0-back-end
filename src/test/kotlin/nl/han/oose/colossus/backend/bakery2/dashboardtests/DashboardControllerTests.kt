package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsController
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class DashboardControllerTests {
    private lateinit var sut: DashboardsController

    private lateinit var dashboardsService: DashboardsService

    private lateinit var headerService: HeaderService

    private lateinit var userService: UserService

    private var team: TeamDto = TeamDto()

    @BeforeEach
    fun setup() {
        sut = DashboardsController()
        dashboardsService = mock(DashboardsService::class.java)
        headerService = mock(HeaderService::class.java)
        userService = mock(UserService::class.java)
        sut.setDashboardsService(dashboardsService)
        sut.setTokenService(headerService)
        sut.setUserService(userService)
        team.setName("testTeam")
        team.setId(1)
    }

    @Test
    fun testGetAllDashboardWorksCorrectly() {

        // Arrange
        val dashboard = DashboardCollectionDto()
        `when`(dashboardsService.getAllDashboards(1)).thenReturn(dashboard)
        `when`(headerService.getUserId()).thenReturn(1)
        // Act
        val response = sut.getAllDashboards().statusCode.value()
        // Assert
        assertEquals(200, response)
        verify(dashboardsService).getAllDashboards(1)
    }

    @Test
    fun testAddDashboardsCorrectly() {

        // Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", team, true)
        doNothing().`when`(userService).checkUserInTeam(0, 1)
        // Act
        val response = sut.addDashboard(dashboard).statusCode.value()
        // Assert
        assertEquals(201, response)
        verify(dashboardsService).addDashboard(dashboard)
        verify(userService).checkUserInTeam(0, 1)
    }

    @Test
    fun testGetDashboardWorksCorrectly() {
        //Arrange
        `when`(headerService.getUserId()).thenReturn(1)
        val dashboard = DashboardDto(1, "test", "test", "test", team, true)
        `when`(dashboardsService.getDashboard(1, 1)).thenReturn(dashboard)
        //Act
        val response = sut.getDashboard(1).statusCode.value()
        //Assert
        assertEquals(200, response)
        verify(dashboardsService).getDashboard(1, 1)
    }

    @Test
    fun testEditDashboardWorksCorrectly() {
        //Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", team, true)
        `when`(headerService.getUserId()).thenReturn(1)
        doNothing().`when`(dashboardsService).editDashboard(dashboard)
        doNothing().`when`(userService).checkUserInTeam(1, 1)
        //Act
        val response = sut.editDashboard(dashboard).statusCode.value()
        //Assert
        assertEquals(200, response)
        verify(headerService).getUserId()
        verify(dashboardsService).editDashboard(dashboard)
        verify(userService).checkUserInTeam(1, 1)
    }

    @Test
    fun testDeleteDashboardsCorrectly() {
        // Arrange
        val dashboardId = 2
        val mockUserId = 1
        val dashboard = DashboardDto(1, "test", "test", "test", team, true)

        `when`(headerService.getUserId()).thenReturn(mockUserId)
        `when`(dashboardsService.getDashboard(dashboardId, mockUserId)).thenReturn(dashboard)
        doNothing().`when`(userService).checkUserInTeam(1, 1)

        // Act
        val response = sut.deleteDashboard(dashboardId).statusCode

        // Assert
        assertEquals(HttpStatus.OK, response)

        verify(headerService).getUserId()
        verify(dashboardsService).deleteDashboard(dashboardId)
        verify(userService).checkUserInTeam(1, 1)
    }
}