package nl.han.oose.colossus.backend.bakery2.dashboardtests

import nl.han.oose.colossus.backend.bakery2.dashboards.*
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*

class dashboardServiceTests {


    private lateinit var sut: DashboardsService

    private lateinit var dashboardsDao: DashboardsDao

    private lateinit var piDao: PiDao


    @BeforeEach
    fun setup() {
        sut = DashboardsServiceImp()
        dashboardsDao = mock(DashboardsDao::class.java)
        sut.setDashboardDao(dashboardsDao)
        piDao = mock(PiDao::class.java)
        sut.setPiDao(piDao)
    }

    @Test
    fun testGetAllDashboardsCallsNextDaoFunction() {
        // Arrange
        `when`(dashboardsDao.getAllDashboards()).thenReturn(DashboardCollectionDto())
        // Act
        sut.getAllDashboards()

        // Assert
        verify(dashboardsDao).getAllDashboards()
    }

    @Test
    fun testAddDashboardsCallsNextDaoFunction() {
        // Arrange
        val dashboard: DashboardDto = DashboardDto(1, "test", "test", "test", 1)
        // Act
        sut.addDashboard(dashboard)

        // Assert
        verify(dashboardsDao).addDashboard(dashboard)
    }

    @Test
    fun testDeleteDashboardCallsNextDaoFunction() {
        // Arrange
        val mockDashboardId = 1
        val mockUserId = 2

        `when`(dashboardsDao.getUserIdFromDashboard(mockDashboardId)).thenReturn(mockUserId)

        // Act & Assert
        assertDoesNotThrow { sut.deleteDashboard(mockDashboardId, mockUserId) }

        verify(piDao).setDashboardsNull(mockDashboardId)
        verify(dashboardsDao).deleteDashboard(mockDashboardId)
    }

//    @Test
//    fun testEditDashboardCallsNextDaoFunction() {
//        // Arrange
//        val dashboard: DashboardDto = DashboardDto(1, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "meme", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images", 1)
//        doNothing().`when`(dashboardsDao).editDashboard(dashboard)
//
//        // Act
//        sut.editDashboard(dashboard,1)
//
//        // Assert
//        verify(dashboardsDao).editDashboard(dashboard)
//    }

}