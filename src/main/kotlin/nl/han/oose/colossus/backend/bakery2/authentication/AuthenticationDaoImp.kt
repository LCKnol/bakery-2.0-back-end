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

    override fun insertUser(userDto: UserDto) {
        val query = "INSERT INTO USERS(firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)"
        try {
            val preparedStatement = databaseConnection.prepareStatement(query)
            preparedStatement.setString(1, userDto.getFirstName())
            preparedStatement.setString(2, userDto.getLastName())
            preparedStatement.setString(3, userDto.getEmail())
            preparedStatement.setString(4, userDto.getPassword())
            preparedStatement.setBoolean(5, userDto.getIsAdmin())
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