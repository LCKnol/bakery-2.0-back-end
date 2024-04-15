package nl.han.oose.colossus.backend.bakery2

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)

    val connection: DatabaseConnection = DatabaseConnection()

        val roomQuery = "SELECT * FROM ROOM"
        val statement = connection.prepareStatement(roomQuery)
        val result = statement.executeQuery()

        while (result.next()) {
            println(result.toString())
        }

}
