package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.dto.UserCollectionDto
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
import org.springframework.web.bind.annotation.RequestBody

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
        val user = UserInfoDto()
        `when`(tokenService.getToken()).thenReturn("fakeToken")
        `when`(userService.getUserInfo("fakeToken")).thenReturn(user)


        // Act
        val response: ResponseEntity<UserInfoDto> = sut.getUserInfo()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
        verify(userService).getUserInfo("fakeToken")
    }

    @Test
    fun testRegisterUserSuccess() {
        // Arrange
        val userDto = UserDto(
            id = 1,
            firstName = "reem",
            lastName = "man",
            email = "reem.@gmail.com",
            password = "mypassword",
            isAdmin = true,
            teams =  ArrayList<TeamDto>()
        )
        Mockito.doNothing().`when`(userService).registerUser(userDto)

        // Act
        val response: ResponseEntity<HttpStatus> = sut.registerUser(userDto)

        // Assert
        verify(userService).registerUser(userDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }


    @Test
    fun testGetAllUsersSuccess() {
        // Act
        val response: ResponseEntity<UserCollectionDto> = sut.getAllUsers()
        // Assert
        verify(userService).getAllUsers()
        assertEquals(HttpStatus.OK, response.statusCode)
    }


    @Test
    fun testDeleteUsersSuccess() {

        // Act
        val response: ResponseEntity<UserCollectionDto> = sut.deleteUser(1)

        // Assert
        verify(userService).deleteUser(1)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun testAssignAdminRightsToUser() {
        //Arrange
        var userDto = UserDto(
            id = 2,
            firstName = "Arnoud",
            lastName = "Visi",
            email = "Avisi@outlook.com",
            password = "mypassword",
            isAdmin = true,
            teams =  ArrayList<TeamDto>()
        )
        // Act
        val response: ResponseEntity<UserCollectionDto> = sut.assignAdminRightsToUser(userDto)

        // Assert
        verify(userService).assignAdminRightsToUser(userDto)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}