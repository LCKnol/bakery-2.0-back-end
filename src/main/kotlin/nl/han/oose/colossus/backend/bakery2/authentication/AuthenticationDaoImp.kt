package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import org.springframework.context.annotation.Primary
import java.sql.Connection
import java.sql.SQLException

@Primary
class AuthenticationDaoImp : AuthenticationDao {
//TODO: DB Connection
private val databaseConnection: Connection = DatabaseConnection.getConnection()


    override fun findPassword(email: String): String {
        val query = "SELECT password FROM user WHERE email = ?"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, email)
            val resultSet = preparedStatement.executeQuery()
            return mapper.map(resultSet)
        } catch (e: SQLException) {
            return "Error accessing database: ${e.message}"
        }
    }


    //TODO: Remove
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

    override fun insertToken(email: String, token: String) {
        val query = "INSERT INTO usersession (userID, token) SELECT userID, ? FROM User WHERE email = ?"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            preparedStatement.setString(2, email)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    override fun insertUser(userDto: UserDto) {
        val query = "INSERT INTO user(firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, userDto.firstName)
            preparedStatement.setString(2, userDto.lastName)
            preparedStatement.setString(3, userDto.email)
            preparedStatement.setString(4, userDto.password)
            preparedStatement.setBoolean(5, userDto.isAdmin)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}