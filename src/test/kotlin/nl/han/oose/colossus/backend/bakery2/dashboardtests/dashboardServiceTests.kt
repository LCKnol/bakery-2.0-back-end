package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert
import nl.han.oose.colossus.backend.bakery2.dashboards.*
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class dashboardServiceTests {


    private lateinit var sut: DashboardsService

    private lateinit var dashboardsDao: DashboardsDao


    @Test
    @BeforeEach
    fun setup() {
        sut = DashboardsServiceImp()
        dashboardsDao = mock(DashboardsDao::class.java)
        sut.setDashboardDao(dashboardsDao)
    }

    @Test
    fun TestGetAllDashboardsCallsNextDaoFunction() {
        // Arrange
        `when`(dashboardsDao.getAllDashboards()).thenReturn(DashboardCollectionDto())
        // Act
        sut.getAllDashboards()

        // Assert
        verify(dashboardsDao).getAllDashboards()
    }

}