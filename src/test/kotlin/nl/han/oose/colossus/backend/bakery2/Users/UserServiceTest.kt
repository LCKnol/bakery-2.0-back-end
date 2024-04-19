package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.Mockito

class UserServiceTest {
    private lateinit var sut: UserServiceImp
    private lateinit var userDao: UserDao

    @Test
    @BeforeEach
    fun setUp() {
        sut = UserServiceImp()
        userDao = Mockito.mock(UserDao::class.java)
        sut.setUserDao(userDao)
    }

    @Test
    fun testGetUserInfo() {
        // Arrange
        val token = "fakeToken"
        val userInfo = UserInfoDto()
        Mockito.`when`(userDao.getUserInfo(token)).thenReturn(userInfo)

        // Act
        val result = sut.getUserInfo(token)

        // Assert
        assert(result == userInfo)
        Mockito.verify(userDao).getUserInfo(token)
    }

    @Test
    fun testGetUser() {
        // Arrange
        val token = "fakeToken"
        val userId = 123
        Mockito.`when`(userDao.getUser(token)).thenReturn(userId)

        // Act
        val result = sut.getUser(token)

        // Assert
        assert(result == userId)
        Mockito.verify(userDao).getUser(token)
    }
}