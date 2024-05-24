package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.dto.UserCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.users.UserDaoImp
import nl.han.oose.colossus.backend.bakery2.users.UserMapper
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.InputStreamReader


class UserDaoTest {

    private lateinit var sut: UserDaoImp
    private lateinit var userMapper: UserMapper
    private lateinit var dbConnection: DatabaseConnection

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
        val token = "fake token"
        val userInfo = UserInfoDto()
        `when`(userMapper.mapUserInfo(MockitoHelper.anyObject())).thenReturn(userInfo)


        // Act
        val result = sut.getUserInfo(token)

        // Assert
        assertEquals(userInfo, result)
    }

    @Test
    fun testGetUser() {
        // Arrange
        dbConnection = DatabaseConnection()
        val insertStatement = dbConnection.getConnection().prepareStatement(
            "INSERT INTO USERSESSION (USERID, TOKEN)" +
                    "VALUES (?, ?);"
        )
        insertStatement.setInt(1, 1)
        insertStatement.setString(2, "123")
        insertStatement.executeUpdate()
        val token: String = "123"
        val user: UserDto =
            UserDto(1, "Arnoud", "Visi", "Avisi@outlook.com", "test password", ArrayList<TeamDto>(), false)
        `when`(userMapper.mapUser(MockitoHelper.anyObject())).thenReturn(user)


        // Act
        val result = sut.getUser(token)

        // Assert
        assertEquals(user, result)
    }

    @Test
    fun insertUser() {
        // Arrange
        val userDto =
            UserDto(-1, "John", "Doe", "john.doe@example.com", "securePassword123", ArrayList<TeamDto>(), true)

        dbConnection = DatabaseConnection()
        val insertStatement = dbConnection.getConnection().prepareStatement(
            "SELECT FIRSTNAME FROM USERS WHERE EMAIL = ?;"
        )
        insertStatement.setString(1, "john.doe@example.com")

        // Act
        sut.insertUser(userDto)

        // Assert
        val resultSet = insertStatement.executeQuery()
        resultSet.next()
        println(resultSet.getString(1))
        val result = resultSet.getString(1)

        assertEquals(result, "John")
    }


    @Test
    fun testGetAllUsersSuccess() {

        //Arrange
        var userCollectionDto = UserCollectionDto()
        // Act
        `when`(userMapper.mapUserCollection(MockitoHelper.anyObject())).thenReturn(userCollectionDto)
        val response: UserCollectionDto = sut.getAllUsers()

        // Assert
        Mockito.verify(userMapper).mapUserCollection(MockitoHelper.anyObject())
        assertEquals(userCollectionDto,response)
    }


    @Test
    fun testDeleteUsersSuccess() {
        dbConnection = DatabaseConnection()
        val statement = dbConnection.getConnection().prepareStatement("SELECT * FROM USERS WHERE USERID = ?")
        statement.setInt(1,1)
        // Act
        sut.deleteUser(1)
        statement.executeQuery()
        var result = statement.resultSet.first()

        // Assert
        assertEquals(false,result)
    }
}
