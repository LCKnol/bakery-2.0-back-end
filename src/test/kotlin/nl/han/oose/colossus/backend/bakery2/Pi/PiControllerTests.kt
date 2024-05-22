package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.pi.PiController
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

class PiControllerTests {

    private lateinit var sut: PiController
    private lateinit var piService: PiService
    private lateinit var userService: UserService
    private lateinit var headerService: HeaderService
    @Test
    @BeforeEach
    fun setUp() {

        piService = mock(PiService::class.java)
        userService = mock(UserService::class.java)
        headerService = mock(HeaderService::class.java)


        sut = PiController()
        sut.setPiService(piService)
        sut.setUserService(userService)
        sut.setTokenService(headerService)
    }

    @Test
    fun testGetPisWorksCorrectly() {
        // Arrange
        val pi = PiCollectionDto()
        `when`(headerService.getUserId()).thenReturn(1)
        `when`(piService.getPis(1)).thenReturn(pi)

        // Act
        val response: ResponseEntity<PiCollectionDto> = sut.getPis()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pi, response.body)
        verify(headerService).getUserId()
        verify(piService).getPis(1)
    }

    @Test
    fun testgetAllPisWorksCorrectly() {
        // Arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(headerService.getUserId()).thenReturn(1)
        `when`(piService.getAllPis()).thenReturn(pi)

        // Act
        val response: ResponseEntity<PiCollectionDto> = sut.getAllPis()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pi, response.body)
        verify(piService).getAllPis()
    }

    @Test
    fun testgetAllPirequestsPisWorksCorrectly() {
        // Arrange
        val piRequest: PiRequestsCollectionDto = PiRequestsCollectionDto()
        `when`(headerService.getUserId()).thenReturn(1)
        `when`(piService.getAllPiRequests()).thenReturn(piRequest)

        // Act
        val response: ResponseEntity<PiRequestsCollectionDto> = sut.getAllPiRequests()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(piRequest, response.body)
        verify(piService).getAllPiRequests()
    }

    @Test
    fun testInitPiWorksCorrectly() {
        // Arrange
        val piDto = PiDto()

        // Act
        val response: ResponseEntity<HttpStatus> = sut.initPi(piDto)
        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        verify(piService).addPi(anyString(), anyString(), anyString())
    }

    @Test
    fun testDeclinePiRequestWorksCorrectly() {
        // Arrange
        val fakeAdress = "fake adress"
        // Act
        val response: ResponseEntity<HttpStatus> = sut.declinePiRequest(fakeAdress)
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(piService).declinePiRequest(fakeAdress)
    }

    @Test
    fun testEditPi() {
        // Arrange
        val piDto = PiDto()
        val token = "validToken"
        val userId = 1

        `when`(headerService.getToken()).thenReturn(token)
        `when`(userService.getUserId(token)).thenReturn(userId)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.editPi(piDto)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(headerService).getToken()
        verify(userService).getUserId(token)
        verify(piService).editPi(piDto, userId)
    }
    @Test
    fun testAssignDashboardToPi() {
        // Arrange
        val piDto = PiDto()

        // Act
        val response: ResponseEntity<HttpStatus> = sut.assignDashboardToPi(piDto)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(piService).assignDashboardToPi(piDto)
    }
    @Test
    fun testRebootPi() {
        // Arrange
        val piId = 1
        // Act
        val response: ResponseEntity<HttpStatus> = sut.rebootPi(piId)
        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify(piService).rebootPi(piId)
    }

}
