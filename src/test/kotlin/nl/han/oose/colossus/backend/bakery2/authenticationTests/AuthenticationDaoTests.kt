package nl.han.oose.colossus.backend.bakery2.authenticationTests

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDaoImp
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDaoImp
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import org.h2.tools.RunScript
import org.mockito.Mockito.`when`
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class AuthenticationDaoTests {

    private lateinit var sut: AuthenticationDaoImp
    private lateinit var databaseConnection: DatabaseConnection
    private lateinit var resultSet: ResultSet

    @BeforeEach
    fun setup() {
        sut = AuthenticationDaoImp()
        val databaseConnection: DatabaseConnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(databaseConnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create (1).sql")!!))
        sut.setDatabaseConnection(databaseConnection)

        sut.setDatabaseConnection(databaseConnection)

        resultSet = mock(ResultSet::class.java)
    }

    @Test
    fun testFindPasswordWithEmailFound() {
        // Arrange
        val email = "Avisi@outlook.com"
        val expectedPassword = "$2a$10"+'$'+"piwNZPAOhMhdG7Xlm/3kkOs/hZeYlfyQPAY/z7SurggdiLxfzu.KC"


        // Act
        val actualPassword = sut.findPassword(email)

        // Assert
        assertEquals(expectedPassword, actualPassword, "The retrieved password should match the expected one.")
    }
}
