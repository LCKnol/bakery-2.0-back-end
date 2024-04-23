package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert
import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dashboards.*
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpForbiddenException
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class dashboardServiceTests {


    private lateinit var sut: DashboardsService

    private lateinit var dashboardsDao: DashboardsDao



    @BeforeEach
    fun setup() {
        sut = DashboardsServiceImp()
        dashboardsDao = mock(DashboardsDao::class.java)
        sut.setDashboardDao(dashboardsDao)
    }

    @Test
    fun TestGetAllDashboardsCallsNextDaoFunction() {
        // Arrange
        val expectedDashboards = DashboardCollectionDto()
        `when`(dashboardsDao.getAllDashboards()).thenReturn(expectedDashboards)

        // Act
        val result = sut.getAllDashboards()

        // Assert
        assertEquals(expectedDashboards, result)
        verify(dashboardsDao).getAllDashboards()
    }

    @Test
    fun TestAddDashboardsCallsNextDaoFunction() {
        // Arrange
        val dashboard = DashboardDto(1,"test","test","test",1)
        // Act
        sut.addDashboard(dashboard)

        // Assert
        verify(dashboardsDao).addDashboard(dashboard)
    }

    @Test
    fun testEditDashboardCallsNextDaoFunction() {
        // Arrange
        val dashboardId = 1
        val userId = 1
        val dashboard = DashboardDto(dashboardId, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "meme", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", userId)
        `when`(dashboardsDao.getUserIdFromDashboard(dashboardId)).thenReturn(userId)
        // Act
        sut.editDashboard(dashboard, userId)
        // Assert
        verify(dashboardsDao).editDashboard(dashboard)
    }

    @Test
    fun testEditDashboardThrowsForbiddenExceptionWhenUserDoesNotHavePermission() {
        // Arrange
        val dashboardId = 1
        val userId = 1
        val dashboard = DashboardDto(dashboardId, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "meme", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", userId)

        `when`(dashboardsDao.getUserIdFromDashboard(dashboardId)).thenReturn(userId + 1)

        // Act & Assert
        assertThrows(HttpForbiddenException::class.java) {
            sut.editDashboard(dashboard, userId)
        }
        verify(dashboardsDao, never()).editDashboard(MockitoHelper.anyObject())
    }

    @Test
    fun testGetDashboardReturnsDashboardDtoWhenDashboardExists() {
        // Arrange
        val dashboardId = 1
        val expectedDashboard = DashboardDto(dashboardId, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "Dashboard", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", 1)
        `when`(dashboardsDao.getDashboard(dashboardId)).thenReturn(expectedDashboard)

        // Act
        val result = sut.getDashboard(dashboardId)

        // Assert
        assertEquals(expectedDashboard, result)
    }

    @Test
    fun testGetDashboardThrowsNotFoundExceptionWhenDashboardDoesNotExist() {
        // Arrange
        val dashboardId = 1
        `when`(dashboardsDao.getDashboard(dashboardId)).thenReturn(null)

        // Act & Assert
        assertThrows(HttpNotFoundException::class.java) {
            sut.getDashboard(dashboardId)
        }
    }


}
