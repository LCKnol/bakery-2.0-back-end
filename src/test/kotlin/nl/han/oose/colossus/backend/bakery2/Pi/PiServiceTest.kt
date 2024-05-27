package nl.han.oose.colossus.backend.bakery2.Pi


import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiServiceImp
import nl.han.oose.colossus.backend.bakery2.pi.PiStatus
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.SocketResponseDto
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
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
        val ipAddress = "123.123.123.123"
        val name = "fake pi"
        val roomNo = "fake room"

        // act
        sut.addPi(macAddress, ipAddress, name, roomNo)

        // assert
        verify(piDao).insertPi(macAddress, ipAddress, name, roomNo)
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
    fun testEditPiCallsDaoWithCorrectParameters() {
        // Arrange
        val piDto = PiDto().apply {
            setId(1)
            setName("Pi Device")
            setStatus("Active")
            setDashboardName("Main Dashboard")
            setMacAddress("00:1A:22:33:44:55")
            setRoomNo("101B")
        }
        val userId = 1234  // Currently not used

        // Act
        sut.editPi(piDto, userId)

        // Assert
        verify(piDao).editPi(piDto)
        verifyNoMoreInteractions(piDao)
    }
    @Test
    fun testGetPiSuccessfully() {
        // Arrange
        val piId = 1
        val expectedPi = PiDto().apply {

            setName("Pi Name")
            setRoomNo("Room 101")
            setMacAddress("00:11:22:33:44:55")
            setStatus("Active")
        }
        `when`(piDao.getPi(piId,expectedPi.getMacAddress())).thenReturn(expectedPi)

        // Act
        val resultPi = sut.getPi(piId,expectedPi.getMacAddress())

        // Assert
        assertNotNull(resultPi)
        assertEquals(expectedPi, resultPi)
        verify(piDao).getPi(piId,expectedPi.getMacAddress())
    }

    @Test
    fun testGetPiThrowsNotFoundException() {
        // Arrange
        val piId = 1
        `when`(piDao.getPi(piId)).thenReturn(null)

        // Act & Assert
        val exception = assertThrows(HttpNotFoundException::class.java) {
            sut.getPi(piId,"0")
        }

        assertEquals("pi does not exist", exception.message)
        verify(piDao).getPi(piId,"0")
    }

    @Test
    fun testRebootPi() {
        // Arrange
        val piId = 1
        val macAddress = "00:11:22:33:44:55"
        `when`(piDao.getMacAddress(piId)).thenReturn(macAddress)

        // Act
        sut.rebootPi(piId)

        // Assert
        verify(piDao).getMacAddress(piId)
        verify(messagingTemplate).convertAndSend(eq("/topic/pi-listener/$macAddress"), any(SocketResponseDto::class.java))
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
        verify(piDao).assignDashboard(piDto.getId(),piDto.getDashboardId())
    }

    @Test
    fun pingPiworksCorrectly() {
        // Arrange
        val piId = 1
        `when`(piDao.getMacAddress(piId)).thenReturn("macAddress")
        // Act
        sut.pingPi(piId)

        // Assert
        verify(piDao).getMacAddress(piId)
    }

    @Test
    fun setPiStatusCallsCorrectly() {
        // Arrange
        val piStatus = PiStatus.OFFLINE
        val piId = 1

        // Act
        sut.setPiStatus(piStatus, piId)

        // Assert
        verify(piDao).updateStatus("OFFLINE", piId)
    }

    @Test
    fun turnTvOnAndOff(){
        // Arrange
        val piId = 1
        val option = true
        val macAddress = "00:11:22:33:44:55"
        `when`(piDao.getMacAddress(piId)).thenReturn(macAddress)

        // Act
        sut.setTvPower(piId,option)

        // Assert
        verify(piDao).getMacAddress(piId)
        verify(messagingTemplate).convertAndSend(eq("/topic/pi-listener/$macAddress"), any(SocketResponseDto::class.java))
    }

    @Test
    fun testUpdateAllPis() {
        // Arrange
        val pi1 = PiDto().apply { setMacAddress("00:11:22:33:44:55") }
        val pi2 = PiDto().apply { setMacAddress("66:77:88:99:AA:BB") }
        val piCollection = PiCollectionDto().apply {
            setPis(listOf(pi1, pi2))
        }
        `when`(piDao.getAllPis()).thenReturn(piCollection)

        // Act
        sut.updateAllPis()

        // Assert

        verify(piDao).getAllPis()
        verify(messagingTemplate).convertAndSend(eq("/topic/pi-listener/${pi1.getMacAddress()}"), any(SocketResponseDto::class.java))
        verify(messagingTemplate).convertAndSend(eq("/topic/pi-listener/${pi2.getMacAddress()}"), any(SocketResponseDto::class.java))
    }
    @Test
    fun testPingAllPis() {
        // Arrange
        val pi1 = PiDto().apply { setMacAddress("00:11:22:33:44:55") }
        val pi2 = PiDto().apply { setMacAddress("66:77:88:99:AA:BB") }
        val piCollection = PiCollectionDto().apply {
            setPis(listOf(pi1, pi2))
        }
        `when`(piDao.getAllPis()).thenReturn(piCollection)

        // Act
        sut.pingAllPis()

        // Assert

        verify(piDao).getAllPis()
    }
    @Test
    fun testRebootAllPis() {
        // Arrange
        val pi1 = PiDto().apply { setMacAddress("00:11:22:33:44:55") }
        val pi2 = PiDto().apply { setMacAddress("66:77:88:99:AA:BB") }
        val piCollection = PiCollectionDto().apply {
            setPis(listOf(pi1, pi2))
        }
        `when`(piDao.getAllPis()).thenReturn(piCollection)

        // Act
        sut.rebootAllPis()

        // Assert

        verify(piDao).getAllPis()
    }
}

