package nl.han.oose.colossus.backend.bakery2.authenticationTests

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDaoImp
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class AuthenticationDaoTests {

    private lateinit var sut: AuthenticationDaoImp
    private lateinit var mockDatabaseConnection: DatabaseConnection
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet

    @BeforeEach
    fun setup() {
        mockDatabaseConnection = mock(DatabaseConnection::class.java)
        mockPreparedStatement = mock(PreparedStatement::class.java)
        mockResultSet = mock(ResultSet::class.java)
        sut = AuthenticationDaoImp().apply {
            setDatabaseConnection(mockDatabaseConnection)
        }

        `when`(mockDatabaseConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
        `when`(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(true)
    }

    @Test
    fun testFindPasswordWithEmailFound() {
        // Arrange
        val email = "avis@outlook.com"
        val expectedPassword = "$2a$10"+'$'+"piwNZPAOhMhdG7Xlm/3kkOs/hZeYlfyQPAY/z7SurggdiLxfzu.KC"
        `when`(mockResultSet.getString("password")).thenReturn(expectedPassword)

        // Act
        val actualPassword = sut.findPassword(email)

        // Assert
        assertEquals(expectedPassword, actualPassword, "The retrieved password should match the expected one.")
    }

    @Test
    fun testDoesNotFindPassword() {
        // Arrange
        val email = "invalid@outlook.com"

        `when`(mockResultSet.next()).thenReturn(false)

        // Act
        val result = sut.findPassword(email)

        // Assert
        assertEquals(result, "Password not found")
    }

    @Test
    fun tokenExistsReturnsTrueWhenTokenIsFound() {
        // Arrange
        `when`(mockResultSet.getInt(1)).thenReturn(1)

        // Act
        val result = sut.tokenExists("existingToken")

        // Assert
        assert(result)
    }

    @Test
    fun tokenExistsReturnsFalseIfTokenINotFound() {
        // Arrange
        `when`(mockResultSet.getInt(1)).thenReturn(0)

        // Act
        val result = sut.tokenExists("nonExistingToken")

        // Assert
        assertFalse(result)
    }
    @Test
    fun tokenExistsReturnsFalseIfSQLExceptionIsThrown() {
        // Arrange
        `when`(mockDatabaseConnection.prepareStatement(Mockito.anyString())).thenThrow(SQLException("Database error"))

        // Act
        val result = sut.tokenExists("anyToken")

        // Assert
        assert(!result)  // Expect false because of SQLException
    }

    @Test
    fun insertToken() {
        // Arrange
        val email = "user@example.com"
        val token = "someRandomToken"
        val expectedQuery = "INSERT INTO USERSESSION (userID, token) SELECT userID, ? FROM USERS WHERE email = ?"

        // Act
        sut.insertToken(email, token)

        // Assert
        Mockito.verify(mockDatabaseConnection).prepareStatement(expectedQuery)
        Mockito.verify(mockPreparedStatement).setString(1, token)
        Mockito.verify(mockPreparedStatement).setString(2, email)
        Mockito.verify(mockPreparedStatement).executeUpdate()
    }




    @Test
    fun deleteSessionDeleteTokenCorrectly() {
        // Arrange
        val token = "someToken123"
        val expectedQuery = "DELETE FROM USERSESSION WHERE token = ?"

        // Act
        sut.deleteSession(token)

        // Assert
        Mockito.verify(mockDatabaseConnection).prepareStatement(expectedQuery)
        Mockito.verify(mockPreparedStatement).setString(1, token)
        Mockito.verify(mockPreparedStatement).executeUpdate()
    }
}