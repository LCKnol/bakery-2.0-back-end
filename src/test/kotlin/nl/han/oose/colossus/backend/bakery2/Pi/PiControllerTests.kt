package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
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
    private lateinit var tokenService: TokenService

    @Test
    @BeforeEach
    fun setUp() {

        piService = mock(PiService::class.java)
        userService = mock(UserService::class.java)
        tokenService = mock(TokenService::class.java)


        sut = PiController()
        sut.setPiService(piService)
        sut.setUserService(userService)
        sut.setTokenService(tokenService)
    }

    @Test
    fun TestGetPisWorksCorrectly() {
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
}
