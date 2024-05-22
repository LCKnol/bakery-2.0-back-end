package nl.han.oose.colossus.backend.bakery2.Pi

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiDaoImp
import nl.han.oose.colossus.backend.bakery2.pi.PiMapper
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.*
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet


class PiDaoTest {

    private lateinit var sut: PiDao

    private lateinit var piMapper: PiMapper

    private lateinit var dbconnection: DatabaseConnection


    @Test
    @BeforeEach
    fun setup() {
        sut = PiDaoImp()
        piMapper = mock(PiMapper::class.java)
        dbconnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setPiMapper(piMapper)
    }

    @Test
    fun TestGetPisCallWorksCorrectly() {

        // arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(piMapper.mapPis(MockitoHelper.anyObject())).thenReturn(pi)

        // act
        val result = sut.getPis(1)

        //assert
        verify(piMapper).mapPis(MockitoHelper.anyObject())
        Assertions.assertEquals(pi, result)
    }

    @Test
    fun testgetAllPisCallWorksCorrectly() {
        // arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(piMapper.mapPis(MockitoHelper.anyObject())).thenReturn(pi)

        // act
        val result = sut.getAllPis()

        //assert
        verify(piMapper).mapPis(MockitoHelper.anyObject())
        Assertions.assertEquals(pi, result)
    }

    @Test
    fun testgetAllPirequestsCallWorksCorrectly() {
        // arrange
        val piRequests: PiRequestsCollectionDto = PiRequestsCollectionDto()
        `when`(piMapper.mapPiRequests(MockitoHelper.anyObject())).thenReturn(piRequests)

        // act
        val result = sut.getAllPiRequests()

        //assert
        verify(piMapper).mapPiRequests(MockitoHelper.anyObject())
        Assertions.assertEquals(piRequests, result)
    }

    @Test
    fun testInsertPiWorksCorrectly() {
        fun insertUser() {
            // Arrange
            val macAddress = "00:11:22:33:44:55"
            val ipAddress = "123.123.123.123"
            val name = "fake pi"
            val roomNo = "fake room"

            dbconnection = DatabaseConnection()
            val selectStatement = dbconnection.getConnection().prepareStatement(
                    "SELECT MACADDRESS FROM PIREQUEST WHERE MACADDRESS = ?"
            )
            selectStatement.setString(1, macAddress)

            // Act
            sut.insertPi(macAddress, ipAddress, name, roomNo)

            // Assert
            val resultSet = selectStatement.executeQuery()
            resultSet.next()
            println(resultSet.getString(1))
            val result = resultSet.getString(1)

            Assertions.assertEquals(result, macAddress)
        }

    }

    @Test
    fun testDeletePiRequestWorksCorrectly() {
        // Arrange
        val adress = "fake adress"

        val statement1 =
                dbconnection.getConnection().prepareStatement("INSERT INTO PIREQUEST (MACADDRESS, IPADDRESS, REQUESTEDON) VALUES (?, ?, ?)")
        statement1.setString(1, adress)
        statement1.setString(2, adress)
        statement1.setString(3, "2024-04-24 09:36:22")

        statement1.execute()

        // Act & Assert
        val statement2 =
                dbconnection.getConnection().prepareStatement("SELECT MACADDRESS FROM PIREQUEST WHERE MACADDRESS = ?")
        statement2.setString(1, adress)
        val resultSet1 = statement2.executeQuery()
        Assertions.assertTrue(resultSet1.next())

        assertDoesNotThrow { sut.deletePiRequest(adress) }

        // Dashboard with id sampleDashboardId should now be deleted
        val statement3 =
                dbconnection.getConnection().prepareStatement("SELECT MACADDRESS FROM PIREQUEST WHERE MACADDRESS = ?")
        statement3.setString(1, adress)
        val resultSet2 = statement3.executeQuery()
        Assertions.assertFalse(resultSet2.next())
    }

    @Test
    fun testAssignDashboardWorksCorrectly(){
        // arrange
        val dashboardId = 2
        val piID = 1
        val macAddress = "macAddress"
        val ipAddress = "ipAddress"

        val statement1 = dbconnection.getConnection().prepareStatement("insert into pi (roomno, dashboardId, name, macaddress, ipaddress) values (15.05, ?, 'testpi', ?, ?)")
        statement1.setInt(1, dashboardId)
        statement1.setString(2, macAddress)
        statement1.setString(3, ipAddress)

        val statement2 = dbconnection.getConnection().prepareStatement("SELECT DASHBOARDID FROM PI WHERE MACADDRESS = ?")
        statement2.setString(1,macAddress)

        // act
        statement1.executeUpdate()
        sut.assignDashboard(dashboardId,piID)
        val resultSet = statement2.executeQuery()
        resultSet.next()
        val result = resultSet.getInt(1)
        //assert
        Assertions.assertEquals(dashboardId, result)
    }
}