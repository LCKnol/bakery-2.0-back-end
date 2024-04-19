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
//private val mapper  = AuthenticationMapperImp()
    @Autowired
private lateinit var databaseConnection: DatabaseConnection


        override fun findPassword(email: String): String {
            val query = "SELECT password FROM users WHERE email = ?"
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
        val query = "INSERT INTO usersession (userID, token) SELECT userID, ? FROM Users WHERE email = ?"
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
        val query = "INSERT INTO users(firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)"
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

    override fun deleteSession(token: String) {
        val query = "DELETE FROM UserSession WHERE token = ?"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, token)
            preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}