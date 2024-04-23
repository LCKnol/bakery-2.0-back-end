package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserService {

    fun getUserInfo(token: String): UserInfoDto

    fun getUserId(token: String): Int

    fun registerUser(userDto: UserDto)
}