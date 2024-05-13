package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiController
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PiControllerTests {

    private lateinit var sut: PiController
    private lateinit var piService: PiService
    private lateinit var userService: UserService
    private lateinit var tokenService: HeaderService

    @Test
    @BeforeEach
    fun setUp() {

        piService = mock(PiService::class.java)
        userService = mock(UserService::class.java)
        tokenService = mock(HeaderService::class.java)


        sut = PiController()
        sut.setPiService(piService)
        sut.setUserService(userService)
        sut.setTokenService(tokenService)
    }

    @Test
    fun testGetPisWorksCorrectly() {
        // Arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(tokenService.getToken()).thenReturn("fakeToken")
        `when`(userService.getUserId("fakeToken")).thenReturn(1)
        `when`(piService.getPis(1)).thenReturn(pi)

        // Act
        val response: ResponseEntity<PiCollectionDto> = sut.getPis()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pi, response.body)
        verify(tokenService).getToken()
        verify(userService).getUserId("fakeToken")
        verify(piService).getPis(1)
    }

    @Test
    fun testgetAllPisWorksCorrectly() {
        // Arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(tokenService.getToken()).thenReturn("fakeToken")
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
        `when`(tokenService.getToken()).thenReturn("fakeToken")
        `when`(piService.getAllPiRequests()).thenReturn(piRequest)

        // Act
        val response: ResponseEntity<PiRequestsCollectionDto> = sut.getAllPiRequests()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(piRequest, response.body)
        verify(piService).getAllPiRequests()
    }
}
