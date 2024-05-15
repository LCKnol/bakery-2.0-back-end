package nl.han.oose.colossus.backend.bakery2.authenticationTests

import junit.framework.Assert
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationController
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class AuthenticationControllerTests {

    private lateinit var sut: AuthenticationController

    private lateinit var authenticationService: AuthenticationService
    private lateinit var headerService : HeaderService


    @Test
    @BeforeEach
    fun setup() {
        sut = AuthenticationController()
       authenticationService = Mockito.mock(AuthenticationService::class.java)
        sut.setAuthenticationService(authenticationService)
        headerService = Mockito.mock(HeaderService::class.java)
        sut.setTokenService(headerService)
    }


    @Test
    fun testLoginSuccess() {
        // Arrange
        val loginRequest = LoginRequestDto()
        loginRequest.setEmail("reem@gmail.com")
        loginRequest.setPassword("mypassword")

        val loginResponse = LoginResponseDto("validToken", true)
        `when`(authenticationService.authenticate(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(loginResponse)

        // Act
        val response: ResponseEntity<LoginResponseDto> = sut.login(loginRequest)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(loginResponse, response.body)
        verify(authenticationService).authenticate("reem@gmail.com", "mypassword")
    }


    @Test
    fun testLogoutSuccess() {
        // Arrange
        val mockToken = "myToken"
        `when`(headerService.getToken()).thenReturn(mockToken)
        Mockito.doNothing().`when`(authenticationService).destroySession(mockToken)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.logout()

        // Assert
        verify(headerService).getToken()
        verify(authenticationService).destroySession(mockToken)
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }



}