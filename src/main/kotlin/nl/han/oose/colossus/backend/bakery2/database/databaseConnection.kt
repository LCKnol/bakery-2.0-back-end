package nl.han.oose.colossus.backend.bakery2.database

import DbProperties
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class DatabaseConnection {
    private val connection: Connection = getConnection()

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?): PreparedStatement {
        return connection.prepareStatement(sql)
    }

    companion object {
        private fun getConnection(): Connection {
            try {
                val dbProperties = DbProperties("database.properties")
                val connectionString = dbProperties.connectionString
                val user = dbProperties.user
                val password = dbProperties.password
                return DriverManager.getConnection(connectionString, user, password)
            } catch (e: SQLException) {
                throw RuntimeException("Failed to establish connection to the database", e)
            }
        }
    }
}
