package nl.han.oose.colossus.backend.bakery2.picommunicator

import jakarta.ws.rs.ServerErrorException
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
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
        val statement =
            databaseConnection.prepareStatement("INSERT INTO PIREQUEST (macAddress, ipAddress, requestedOn) VALUES(?, ?, NOW())")
        statement.setString(1, macAddress)
        statement.setString(2, ipAddress)
        statement.executeUpdate()
        statement.close()
    }

    override fun checkPiExists(request: PiSignUpRequestDto): Boolean {
        val connection = databaseConnection.getConnection()
        val statement = databaseConnection.prepareStatement("SELECT macAddress FROM PI WHERE macAddress = ?")
        statement.setString(1, request.getMacAddress())
        val resultSet = statement.executeQuery()
        val result = resultSet.next()
        statement.close()
        connection.close()
        return result
    }
}