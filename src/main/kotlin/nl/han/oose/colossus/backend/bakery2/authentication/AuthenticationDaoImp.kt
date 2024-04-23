package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException

@Primary
@Component
class AuthenticationDaoImp : AuthenticationDao {
    //TODO: DB Connection

    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    override fun setDatabaseConnection(databaseConnection: DatabaseConnection) {
        this.databaseConnection = databaseConnection
    }

    override fun findPassword(email: String): String {
        val query = "SELECT password FROM USERS WHERE email = ?"
        var password: String? = null
        try {
            databaseConnection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setString(1, email)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        password = resultSet.getString("password")
                    }
                }
            }
        } catch (e: SQLException) {
            return "Error accessing database: ${e.message}"
        }
        return password ?: "Password not found"
    }

    override fun tokenExists(token: String): Boolean {
        val query = "SELECT COUNT(*) FROM USERSESSION WHERE token = ?"
        return try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next() && resultSet.getInt(1) > 0
        } catch (e: SQLException) {
            false
        }
    }

    override fun insertToken(email: String, token: String) {
        val query = "INSERT INTO USERSESSION (userID, token) SELECT userID, ? FROM USERS WHERE email = ?"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            preparedStatement.setString(2, email)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }


    override fun deleteSession(token: String) {
        val query = "DELETE FROM USERSESSION WHERE token = ?"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}