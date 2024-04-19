package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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

        piService = Mockito.mock(PiService::class.java)
        userService = Mockito.mock(UserService::class.java)
        tokenService = Mockito.mock(TokenService::class.java)


        sut = PiController()
        sut.setPiService(piService)
        sut.setUserService(userService)
        sut.setTokenService(tokenService)
    }

    @Test
    fun TestGetPisWorksCorrectly() {
        // Arrange
        val pi: PiCollectionDto = PiCollectionDto()
        Mockito.`when`(tokenService.getToken()).thenReturn("fakeToken")
        Mockito.`when`(userService.getUser("fakeToken")).thenReturn(1)
        Mockito.`when`(piService.getPis(1)).thenReturn(pi)

        // Act
        val response: ResponseEntity<PiCollectionDto> = sut.getPis()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pi, response.body)
        Mockito.verify(tokenService).getToken()
        Mockito.verify(userService).getUser("fakeToken")
        Mockito.verify(piService).getPis(1)
    }
}
