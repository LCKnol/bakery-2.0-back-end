package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.Pi.PiController
import nl.han.oose.colossus.backend.bakery2.Pi.PiService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class UserControllerTest {

    private lateinit var sut: UserController
    private lateinit var userService: UserService
    private lateinit var tokenService: TokenService

    @Test
    @BeforeEach
    fun setUp() {

        userService = Mockito.mock(UserService::class.java)
        tokenService = Mockito.mock(TokenService::class.java)


        sut = UserController()
        sut.setUserService(userService)
        sut.setTokenService(tokenService)
    }

    @Test
    fun TestGetPisWorksCorrectly() {
        // Arrange
        val user: UserInfoDto = UserInfoDto()
        Mockito.`when`(tokenService.getToken()).thenReturn("fakeToken")
        Mockito.`when`(userService.getUserInfo("fakeToken")).thenReturn(user)


        // Act
        val response: ResponseEntity<UserInfoDto> = sut.getUserInfo()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
        Mockito.verify(tokenService).getToken()
        Mockito.verify(userService).getUserInfo("fakeToken")
    }
}
