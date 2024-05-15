package nl.han.oose.colossus.backend.bakery2.dashboardtests

import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dashboards.*
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*

class DashboardServiceTests {

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
        val expectedDashboards = DashboardCollectionDto()
        `when`(dashboardsDao.getAllDashboards(1)).thenReturn(expectedDashboards)

        // Act
        val result = sut.getAllDashboards(1)

        // Assert
        assertEquals(expectedDashboards, result)
        verify(dashboardsDao).getAllDashboards(1)
    }

    @Test
    fun testAddDashboardsCallsNextDaoFunction() {
        // Arrange
        val dashboard = DashboardDto(1, "test", "test", "test", TeamDto(), true)

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

        // Act & Assert
        assertDoesNotThrow { sut.deleteDashboard(mockDashboardId) }

        verify(piDao).removeDashboardFromPis(mockDashboardId)
        verify(dashboardsDao).deleteDashboard(mockDashboardId)
    }

    @Test
    fun testEditDashboardCallsNextDaoFunction() {
        // Arrange
        val teamId = 1
        val teamName = "testTeam"
        val team = TeamDto()
        team.setName(teamName)
        team.setId(teamId)

        val dashboardId = 1
        val dashboard = DashboardDto(
            dashboardId,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "meme",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images",
            team,
            true
        )

        // Act
        sut.editDashboard(dashboard)

        // Assert
        verify(dashboardsDao).editDashboard(dashboard)
    }

    @Test
    fun testGetDashboardReturnsDashboardDtoWhenDashboardExists() {
        // Arrange
        val dashboardId = 1
        val expectedDashboard = DashboardDto(
            dashboardId,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "Dashboard",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images",
            TeamDto(),
            true
        )
        `when`(dashboardsDao.getDashboardForUser(dashboardId, 1)).thenReturn(expectedDashboard)

        // Act
        val result = sut.getDashboard(dashboardId, 1)

        // Assert
        assertEquals(expectedDashboard, result)
    }

    @Test
    fun testGetDashboardThrowsNotFoundExceptionWhenDashboardDoesNotExist() {
        // Arrange
        val dashboardId = 1
        `when`(dashboardsDao.getDashboardForUser(dashboardId, 1)).thenReturn(null)

        // Act & Assert
        assertThrows(HttpNotFoundException::class.java) {
            sut.getDashboard(dashboardId, 1)
        }
    }
}