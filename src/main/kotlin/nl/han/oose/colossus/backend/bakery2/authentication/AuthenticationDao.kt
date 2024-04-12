package nl.han.oose.colossus.backend.bakery2.authentication

interface AuthenticationDao {
    //TODO: DB Connection
    fun isValidUser(email: String, password: String): Boolean
    fun tokenExists(token: String): Boolean
    fun insertToken(token: String)
}