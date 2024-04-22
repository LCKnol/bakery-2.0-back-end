package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.Pi.PiMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserDao {
    fun getUserInfo(token: String): UserInfoDto

    fun getUser(token: String): UserDto?

    fun setUserMapper(mapper: UserMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
}