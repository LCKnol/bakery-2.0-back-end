package nl.han.oose.colossus.backend.bakery2.picommunicator

import jakarta.ws.rs.ServerErrorException
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PiSignUpDaoImp : PiSignUpDao {


    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    @Throws(ServerErrorException::class)
    override fun insertSignUpRequest(macAddress: String) {
        val statement = databaseConnection.prepareStatement("INSERT INTO PiRequest (macAddress, requestedOn) VALUES(?, NOW())")
        statement.setString(1, macAddress)
        statement.executeUpdate()
        statement.close()
    }

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        databaseConnection = connection
    }
}