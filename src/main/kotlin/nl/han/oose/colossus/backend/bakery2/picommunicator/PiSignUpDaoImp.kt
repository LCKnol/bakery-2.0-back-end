package nl.han.oose.colossus.backend.bakery2.picommunicator

import jakarta.ws.rs.ServerErrorException
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PiSignUpDaoImp : PiSignUpDao {

    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        databaseConnection = connection
    }

    @Throws(ServerErrorException::class)
    override fun insertSignUpRequest(macAddress: String, ipAddress: String) {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("INSERT INTO PIREQUEST (macAddress, ipAddress, requestedOn) VALUES(?, ?, NOW())")
        statement.setString(1, macAddress)
        statement.setString(2, ipAddress)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun checkPiExists(macAddress: String): Boolean {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT macAddress FROM PI WHERE macAddress = ?")
        statement.setString(1, macAddress)
        val resultSet = statement.executeQuery()
        val result = resultSet.next()
        statement.close()
        connection.close()
        return result
    }

    @Throws(ServerErrorException::class)
    override fun checkPiSignUpExists(macAddress: String): Boolean {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT macAddress FROM PIREQUEST WHERE macAddress = ?")
        statement.setString(1, macAddress)
        val resultSet = statement.executeQuery()
        val result = resultSet.next()
        statement.close()
        connection.close()
        return result
    }
}