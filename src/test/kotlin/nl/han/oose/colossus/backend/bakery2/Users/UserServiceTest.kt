package nl.han.oose.colossus.backend.bakery2.Users

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.users.UserDao
import nl.han.oose.colossus.backend.bakery2.users.UserServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.Mockito.*

class UserServiceTest {
    private lateinit var sut: UserServiceImp
    private lateinit var userDao: UserDao

    @Test
    @BeforeEach
    fun setUp() {
        sut = UserServiceImp()
        userDao = mock(UserDao::class.java)
        sut.setUserDao(userDao)
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
        val user : UserDto = UserDto(1, "pieter", "post", "123", "123", true)
        `when`(userDao.getUser(token)).thenReturn(user)

        // Act
        val result = sut.getUserId(token)

        // Assert
        assertEquals(result, user.getId())
        verify(userDao).getUser(token)
    }
}