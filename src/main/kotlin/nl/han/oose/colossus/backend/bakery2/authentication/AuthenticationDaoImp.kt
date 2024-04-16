package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import org.springframework.context.annotation.Primary
import java.sql.Connection
import java.sql.SQLException

@Primary
class AuthenticationDaoImp : AuthenticationDao {
//TODO: DB Connection
private val databaseConnection: Connection = DatabaseConnection.getConnection()
    override fun isValidUser(email: String, password: String): Boolean {
        val query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?"
        return try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, email)
            preparedStatement.setString(2, password)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next() && resultSet.getInt(1) > 0
        } catch (e: SQLException) {
            false
        }
    }

    override fun tokenExists(token: String): Boolean {
        val query = "SELECT COUNT(*) FROM usersession WHERE token = ?"
        return try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next() && resultSet.getInt(1) > 0
        } catch (e: SQLException) {
            false
        }
    }

    override fun insertToken(token: String) {
        val query = "INSERT INTO usersession (token) VALUES (?)"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {

        }
    }
}