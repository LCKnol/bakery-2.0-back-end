package nl.han.oose.colossus.backend.bakery2.Users

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import nl.han.oose.colossus.backend.bakery2.dto.*
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpForbiddenException
import nl.han.oose.colossus.backend.bakery2.teams.TeamDao
import nl.han.oose.colossus.backend.bakery2.users.UserDao
import nl.han.oose.colossus.backend.bakery2.users.UserServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt

class UserServiceTest {
    private lateinit var sut: UserServiceImp
    private lateinit var userDao: UserDao
    private lateinit var teamDao: TeamDao

    @Test
    @BeforeEach
    fun setUp() {
        sut = UserServiceImp()
        userDao = mock(UserDao::class.java)
        teamDao = mock(TeamDao::class.java)
        sut.setUserDao(userDao)
        sut.setTeamDao(teamDao)
    }

    @Test
    fun testGetUserInfo() {
        // Arrange
        val token = "fakeToken"
        val userInfo = UserInfoDto()
        `when`(userDao.getUserInfo(token)).thenReturn(userInfo)

        // Act
        val result = sut.getUserInfo(token)

        // Assert
        assertSame(userInfo, result)
        verify(userDao).getUserInfo(token)
    }


    @Test
    fun testGetUser() {
        // Arrange
        val token = "fakeToken"
        val user : UserDto = UserDto(1, "pieter", "post", "123", "123", ArrayList<TeamDto>(),true)
        `when`(userDao.getUser(token)).thenReturn(user)

        // Act
        val result = sut.getUserId(token)

        // Assert
        assertEquals(result, user.getId())
        verify(userDao).getUser(token)
    }

    @Test
    fun registerUserSucceed() {
        // Arrange
        val userDto = UserDto(
            id = 1,
            firstName = "reem",
            lastName = "man",
            email = "reem.@gmail.com",
            password = "mypassword",
            isAdmin = true,
            teams = ArrayList<TeamDto>()
        )

        val userPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt())
        userDto.setPassword(userPassword)

        doNothing().`when`(userDao).insertUser(userDto)
        // Act
        sut.registerUser(userDto)

        // Assert
        verify(userDao).insertUser(userDto)
    }

    @Test
    fun userIsInTeam() {
        // Arrange
        val teamId = 1
        val userId = 2
        val teamCollection = TeamCollectionDto()
        val team1 = TeamDto()
        val teamList = ArrayList<TeamDto>()

        team1.setId(1)
        teamList.add(team1)
        teamCollection.setTeamCollection(teamList)

        `when`(teamDao.getTeamsFromUser(userId)).thenReturn(teamCollection)

        // Act
        sut.checkUserInTeam(userId, teamId)

        // Assert
        verify(teamDao).getTeamsFromUser(userId)
    }

    @Test
    fun userIsNotInTeam() {
        // Arrange
        val teamId = 1
        val userId = 2
        val teamCollection = TeamCollectionDto()
        val team1 = TeamDto()
        val teamList = ArrayList<TeamDto>()

        team1.setId(2)
        teamList.add(team1)
        teamCollection.setTeamCollection(teamList)

        `when`(teamDao.getTeamsFromUser(userId)).thenReturn(teamCollection)

        // Act & Assert
        assertThrows<HttpForbiddenException> {sut.checkUserInTeam(userId, teamId)  }
    }

    @Test
    fun testGetAllUsersSuccess() {

        //Arrange
        var userCollectionDto = UserCollectionDto()
        // Act
        `when`(userDao.getAllUsers()).thenReturn(userCollectionDto)
        val response: UserCollectionDto = sut.getAllUsers()

        // Assert
        verify(userDao).getAllUsers()
        Assertions.assertEquals(userCollectionDto,response)
    }


    @Test
    fun testDeleteUsersSuccess() {
        // Act
        sut.deleteUser(1)

        // Assert
        verify(userDao).deleteUser(1)
    }
}