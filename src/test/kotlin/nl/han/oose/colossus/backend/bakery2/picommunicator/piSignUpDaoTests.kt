package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito

class piSignUpDaoTests {

    private lateinit var sut: PiSignUpDao

    private lateinit var dbconnection: DatabaseConnection

    @BeforeEach
    fun setup() {
        sut = PiSignUpDaoImp()
        dbconnection = DatabaseConnection()
        val scriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
    }

    @Test
    fun insertPyRequestsGoesGood() {
        // Arrange
        val mac = "macAddress"
        val statement = dbconnection.getConnection().prepareStatement("select macaddress from pirequest where macaddress = ?")
        statement.setString(1, mac)

        // Act
        sut.insertSignUpRequest("macAddress", "ipAddress")

        // Assert
        val resultSet = statement.executeQuery()
        resultSet.next()
        val result = resultSet.getString("macAddress")
        Assertions.assertEquals(result, mac)
    }

    @Test
    fun checkPiExistsgetsMacadress() {
        // Arrange
        val piSignUpRequestDto = PiSignUpRequestDto()
        piSignUpRequestDto.setMacAddress("cc:41:00:f3:81:fc")

        //Act
        val result = sut.checkPiExists(piSignUpRequestDto.getMacAddress())

        //Assert
        Assertions.assertEquals(result,true)
    }

    @Test
    fun checkPiSignUpExistsReturnsTrue() {
        // Arrange
        val macAddress = "mac address"
        val ipAddress = "ip address"
        val connection = dbconnection.getConnection()
        val statement = connection.prepareStatement("insert into pirequest (macaddress, ipAddress, requestedon) values (?, ?, NOW())")
        statement.setString(1, macAddress)
        statement.setString(2, ipAddress)
        statement.executeUpdate()

        // Act
        val result = sut.checkPiSignUpExists(macAddress)

        // Assert
        Assertions.assertTrue(result)
    }

    @Test
    fun checkPiSignUpExistsReturnsFalse() {
        // Arrange
        val macAddress = "non existant"
        val connection = dbconnection.getConnection()
        val statement = connection.prepareStatement("delete from pirequest")
        statement.executeUpdate()

        // Act
        val result = sut.checkPiSignUpExists(macAddress)

        // Assert
        Assertions.assertFalse(result)
    }
}