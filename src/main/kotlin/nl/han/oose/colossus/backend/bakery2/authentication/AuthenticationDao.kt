package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.UserDto

interface AuthenticationDao {
    //TODO: DB Connection



    fun findPassword(email: String): String
    fun isValidUser(email: String, password: String): Boolean
    fun tokenExists(token: String): Boolean
    fun insertToken(email: String, token: String)
    fun insertUser(userDto: UserDto)
}