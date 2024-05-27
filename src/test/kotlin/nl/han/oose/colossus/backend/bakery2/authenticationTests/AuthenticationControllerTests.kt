package nl.han.oose.colossus.backend.bakery2.authenticationTests

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import junit.framework.Assert
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationController
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserService
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
    private lateinit var userService: UserService
    private lateinit var googleAuthService: GoogleAuthService
    private lateinit var headerService : HeaderService


    @Test
    @BeforeEach
    fun setup() {
        sut = AuthenticationController()
        authenticationService = mock(AuthenticationService::class.java)
        headerService = mock(HeaderService::class.java)
        userService = mock(UserService::class.java)
        googleAuthService = mock(GoogleAuthService::class.java)
        sut.setAuthenticationService(authenticationService)
        sut.setTokenService(headerService)
        sut.setUserService(userService)
        sut.setGoogleAuthService(googleAuthService)
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

    @Test
    fun testSignInWithGoogle() {
        // Arrange
        val googleTokenDto = GoogleTokenDto()
        googleTokenDto.setJwtToken("test token")
        val googleIdToken = GoogleIdToken.Payload()
        googleIdToken.email = "email"
        val loginResponse = LoginResponseDto("fake token", false)
        `when`(googleAuthService.verifyToken(googleTokenDto.getJwtToken())).thenReturn(googleIdToken)
        `when`(authenticationService.handleGoogleSignIn(googleIdToken.email, userService.emailExists(googleIdToken.email))).thenReturn(loginResponse)
        `when`(userService.emailExists(googleIdToken.email)).thenReturn(true)

        // Act
        val response = sut.signInWithGoogle(googleTokenDto)

        // Assert
        verify(googleAuthService).verifyToken(googleTokenDto.getJwtToken())
        verify(authenticationService).handleGoogleSignIn(googleIdToken.email, true)
        verify(userService, Mockito.times(2)).emailExists(googleIdToken.email)
        assertEquals(HttpStatus.OK, response.statusCode)

    }

    @Test
    fun testRegisterGoogleUser() {

        //Arrange
        val googleTokenDto = GoogleTokenDto()
        googleTokenDto.setJwtToken("test token")
        val newGoogleUserDto = NewGoogleUserDto()
        newGoogleUserDto.setJwtToken(googleTokenDto.getJwtToken())
        newGoogleUserDto.setUserDto(UserDto(1, "test",
            "user", "test@gmail.com",
            "password", ArrayList<TeamDto>(), false))
        val googleIdToken = GoogleIdToken.Payload()

        `when`(googleAuthService.verifyToken(googleTokenDto.getJwtToken())).thenReturn(googleIdToken)
        doNothing().`when`(userService).registerUser(newGoogleUserDto.getUserDto())

        //Act
        val response = sut.registerGoogleUser(newGoogleUserDto)

        //Assert
        verify(googleAuthService).verifyToken(googleTokenDto.getJwtToken())
        verify(userService).registerUser(newGoogleUserDto.getUserDto())
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun testVerifyJwtToken() {

        //Arrange
        val googleTokenDto = GoogleTokenDto()
        googleTokenDto.setJwtToken("test token")
        val googleIdToken = GoogleIdToken.Payload()

        `when`(googleAuthService.verifyToken(googleTokenDto.getJwtToken())).thenReturn(googleIdToken)

        //Act
        val response = sut.verifyJwtToken(googleTokenDto)

        //Assert
        verify(googleAuthService).verifyToken(googleTokenDto.getJwtToken())
        assertEquals(HttpStatus.OK, response.statusCode)

    }

}