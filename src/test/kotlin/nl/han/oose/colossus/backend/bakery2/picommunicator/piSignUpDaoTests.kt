package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import org.junit.jupiter.api.Assertions

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
        sut.insertSignUpRequest("macAddress")

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
        val result = sut.checkPiExists(piSignUpRequestDto)

        //Assert
        Assertions.assertEquals(result,true)
    }
}