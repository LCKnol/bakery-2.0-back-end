package nl.han.oose.colossus.backend.bakery2.authenticationTests

import junit.framework.Assert
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationController
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class AuthenticationControllerTests {

    private lateinit var sut: AuthenticationController

    private lateinit var authenticationService: AuthenticationService
    private lateinit var tokenService : TokenService


    @Test
    @BeforeEach
    fun setup() {
        sut = AuthenticationController()
       authenticationService = Mockito.mock(AuthenticationService::class.java)
        sut.setAuthenticationService(authenticationService)
        tokenService = Mockito.mock(TokenService::class.java)
        sut.setTokenService(tokenService)
    }


    @Test
    fun testLoginSuccess() {
        // Arrange
        val loginRequest = LoginRequestDto()
        loginRequest.setEmail("reem@gmail.com")
        loginRequest.setPassword("mypassword")

        val loginResponse = LoginResponseDto("validToken")
        `when`(authenticationService.authenticate(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(loginResponse)

        // Act
        val response: ResponseEntity<LoginResponseDto> = sut.login(loginRequest)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(loginResponse, response.body)
    }

    @Test
    fun testLoginAuthenticationCall() {
        // Arrange
        val loginRequest = LoginRequestDto()
        loginRequest.setEmail("reem@gmail.com")
        loginRequest.setPassword("reem@gmail.com")

        `when`(authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(LoginResponseDto("validToken"))

        // Act
        sut.login(loginRequest)

        // Assert
        verify(authenticationService).authenticate("reem@gmail.com", "reem@gmail.com")
    }


    @Test
    fun testLogoutSuccess() {
        // Arrange
        val mockToken = "myToken"
        Mockito.`when`(tokenService.getToken()).thenReturn(mockToken)
        Mockito.doNothing().`when`(authenticationService).destroySession(mockToken)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.logout()

        // Assert
        verify(tokenService).getToken()
        verify(authenticationService).destroySession(mockToken)
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
    @Test
    fun testRegisterUserSuccess() {
        // Arrange
        val userDto = UserDto(
            id = 1,
            firstname = "reem",
            lastname = "man",
            email = "reem.@gmail.com",
            password = "mypassword",
            isAdmin = true
        )
        Mockito.doNothing().`when`(authenticationService).registerUser(userDto)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.registerUser(userDto)

        // Assert
        verify(authenticationService).registerUser(userDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }


}