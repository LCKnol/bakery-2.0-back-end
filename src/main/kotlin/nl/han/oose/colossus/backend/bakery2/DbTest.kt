package nl.han.oose.colossus.backend.bakery2

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.*

class DbTest {
    private val connection: DatabaseConnection = DatabaseConnection()
    fun main(args: Array<String>) {

            val roomQuery = "SELECT * FROM ROOM"
            val statement = connection.prepareStatement(roomQuery)
            val result = statement.executeQuery()

        while (result.next()) {
            println(result.toString())
        }

    }
}