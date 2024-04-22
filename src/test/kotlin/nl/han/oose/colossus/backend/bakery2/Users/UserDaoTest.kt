package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.InputStreamReader


class UserDaoTest {

    private lateinit var sut: UserDaoImp
    private lateinit var userMapper: UserMapper
    private lateinit var dbConnection: DatabaseConnection

    @Test
    @BeforeEach
    fun setUp() {
        sut = UserDaoImp()
        userMapper = mock(UserMapper::class.java)
        dbConnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbConnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbConnection)
        sut.setUserMapper(userMapper)
    }

    @Test
    fun testGetUserInfo() {
        // Arrange
        val token: String = "fake token"
        val userInfo: UserInfoDto = UserInfoDto()
        `when`(userMapper.mapUserInfo(MockitoHelper.anyObject())).thenReturn(userInfo)


        // Act
        val result = sut.getUserInfo(token)

        // Assert
        assertEquals(userInfo, result)
    }

    @Test
    fun testGetUser() {
        // Arrange
        val token: String = "123"
        val user : UserDto = UserDto(1, "pieter", "post", "123", "123", true)

        // Act
        val result = sut.getUser(token)

        // Assert
        Assertions.assertEquals(user, result)
    }
}
