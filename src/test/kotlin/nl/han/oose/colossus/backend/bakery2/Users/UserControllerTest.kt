package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserController
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class UserControllerTest {

    private lateinit var sut: UserController
    private lateinit var userService: UserService
    private lateinit var tokenService: HeaderService

    @Test
    @BeforeEach
    fun setUp() {

        userService = mock(UserService::class.java)
        tokenService = mock(HeaderService::class.java)


        sut = UserController()
        sut.setUserService(userService)
        sut.setTokenService(tokenService)
    }

    @Test
    fun testGetPisWorksCorrectly() {
        // Arrange
        val user: UserInfoDto = UserInfoDto()
        `when`(tokenService.getToken()).thenReturn("fakeToken")
        `when`(userService.getUserInfo("fakeToken")).thenReturn(user)


        // Act
        val response: ResponseEntity<UserInfoDto> = sut.getUserInfo()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
        verify(tokenService).getToken()
        verify(userService).getUserInfo("fakeToken")
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
        Mockito.doNothing().`when`(userService).registerUser(userDto)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.registerUser(userDto)

        // Assert
        verify(userService).registerUser(userDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }
}
