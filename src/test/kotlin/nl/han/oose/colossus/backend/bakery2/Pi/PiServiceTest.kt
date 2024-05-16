package nl.han.oose.colossus.backend.bakery2.Pi


import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate

class PiServiceTest {

    private lateinit var sut: PiService

    private lateinit var piDao: PiDao
    private lateinit var dashboardsDao: DashboardsDao
    private lateinit var messagingTemplate: SimpMessagingTemplate

    @BeforeEach
    fun setUp() {
        sut = PiServiceImp()
        dashboardsDao = mock(DashboardsDao::class.java)
        piDao = mock(PiDao::class.java)
        messagingTemplate = mock(SimpMessagingTemplate::class.java)
        sut.setMessagingTemplate(messagingTemplate)
        sut.setDashboardDao(dashboardsDao)
        sut.setPiDao(piDao)
    }

    @Test
    fun testGetPis() {
        // Arrange
        val expectedPis: PiCollectionDto = PiCollectionDto()
        `when`(piDao.getPis(1)).thenReturn(expectedPis)

        // Act
        val actualPis: PiCollectionDto = sut.getPis(1)

        // Assert
        verify(piDao).getPis(1)
        assertEquals(expectedPis, actualPis)
    }

    @Test
    fun testGetAllPis() {
        // Arrange
        val expectedPis: PiCollectionDto = PiCollectionDto()
        `when`(piDao.getAllPis()).thenReturn(expectedPis)

        // Act
        val actualPis: PiCollectionDto = sut.getAllPis()

        // Assert
        verify(piDao).getAllPis()
        assertEquals(expectedPis, actualPis)
    }


    @Test
    fun testGetAllPiRequests() {
        // Arrange
        val expectedPiRequests: PiRequestsCollectionDto = PiRequestsCollectionDto()
        `when`(piDao.getAllPiRequests()).thenReturn(expectedPiRequests)

        // Act
        val actualPiRequests: PiRequestsCollectionDto = sut.getAllPiRequests()

        // Assert
        verify(piDao).getAllPiRequests()
        assertEquals(expectedPiRequests, actualPiRequests)
    }

    @Test
    fun testAddpi() {
        // arrange
        val macAddress = "00:11:22:33:44:55"
        val name = "fake pi"
        val roomNo = "fake room"

        // act
        sut.addPi(macAddress, name, roomNo)

        // assert
        verify(piDao).insertPi(macAddress, name, roomNo)
        verify(piDao).deletePiRequest(macAddress)

    }

    @Test
    fun testDeclinePiRequest() {
        // arrange
        val macAddress = "00:11:22:33:44:55"

        // act
        sut.declinePiRequest(macAddress)

        // assert
        verify(piDao).deletePiRequest(macAddress)

    }

    @Test
    fun testAssignDashboardToPi() {
        // Arrange
        val piDto = PiDto()
        piDto.setDashboardId(1)
        piDto.setId(1)
        `when`(dashboardsDao.getDashboardUrl(piDto.getDashboardId())).thenReturn("URL")
        // Act
        sut.assignDashboardToPi(piDto)
        // Assert
        verify(dashboardsDao).getDashboardUrl(1)
        verify(piDao).assignDashboard(piDto)
    }
}
