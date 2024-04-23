package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserDao {
    fun getUserInfo(token: String): UserInfoDto

    fun getUser(token: String): UserDto?

    fun insertUser(userDto: UserDto)

}