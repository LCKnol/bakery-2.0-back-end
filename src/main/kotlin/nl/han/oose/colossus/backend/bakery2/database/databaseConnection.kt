package nl.han.oose.colossus.backend.bakery2.database


import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException





class DatabaseConnection {
    private val connection: Connection? = null
    @Throws(SQLException::class)
    fun prepareStatement(sql: String?): PreparedStatement {
        return connection!!.prepareStatement(sql)
    }

    companion object {
        fun getConnection(): Connection {
            try {
                val dbProperties: DbProperties = DbProperties("database.properties")
                val connectionString: String = dbProperties.connectionString
                val user: String = dbProperties.user
                val password: String = dbProperties.password
                return DriverManager.getConnection(connectionString, user, password)
            } catch (e: SQLException) {
                throw RuntimeException("Failed to establish connection to the database", e)
            }
        }
    }
}