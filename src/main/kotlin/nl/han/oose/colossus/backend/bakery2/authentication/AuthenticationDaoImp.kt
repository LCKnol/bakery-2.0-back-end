package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import java.sql.Connection
import java.sql.SQLException

@Primary
@Component
class AuthenticationDaoImp : AuthenticationDao {

    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    override fun setDatabaseConnection(databaseConnection: DatabaseConnection) {
        this.databaseConnection = databaseConnection
    }

    @Throws(ServerErrorException::class)
    override fun findPassword(email: String): String {
        val connection = databaseConnection.getConnection()
        val query = "SELECT password FROM USERS WHERE email = ?"
        var password: String? = null
        connection.prepareStatement(query).use { preparedStatement ->
            preparedStatement.setString(1, email)
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    password = resultSet.getString("password")
                }
            }
            preparedStatement.close()
        }
        connection.close()
        return password ?: "Password not found"
    }

    @Throws(ServerErrorException::class)
    override fun tokenExists(token: String): Boolean {
        val connection = databaseConnection.getConnection()
        val query = "SELECT COUNT(*) FROM USERSESSION WHERE token = ?"

        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val result = resultSet.next() && resultSet.getInt(1) > 0
        preparedStatement.close()
        connection.close()
        return result
    }

    @Throws(ServerErrorException::class)
    override fun insertToken(email: String, token: String) {
        val connection = databaseConnection.getConnection()
        val query = "INSERT INTO USERSESSION (userID, token) SELECT userID, ? FROM USERS WHERE email = ?"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, token)
        preparedStatement.setString(2, email)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun deleteSession(token: String) {
        val connection = databaseConnection.getConnection()
        val query = "DELETE FROM USERSESSION WHERE token = ?"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, token)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }
}