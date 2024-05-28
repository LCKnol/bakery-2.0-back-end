package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.*

interface UserDao {
    fun getUserInfo(token: String): UserInfoDto

    fun getUser(token: String): UserDto?

    fun setUserMapper(mapper: UserMapper)

    fun setDatabaseConnection(connection: DatabaseConnection)

    fun insertUser(userDto: UserDto)
    fun getAllUsers(): UserCollectionDto
    fun deleteUser(userId: Int)

    fun emailExists(email: String): Boolean
    fun assignAdminRightsToUser(userDto: UserDto)
}