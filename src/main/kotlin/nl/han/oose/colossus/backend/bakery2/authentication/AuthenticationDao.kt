package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto

interface AuthenticationDao {

    fun tokenExists(token: String): Boolean

    fun insertToken(email: String, token: String)

    fun deleteSession(token: String)

    fun setDatabaseConnection(databaseConnection: DatabaseConnection)

    fun findPassword(email: String): String
}