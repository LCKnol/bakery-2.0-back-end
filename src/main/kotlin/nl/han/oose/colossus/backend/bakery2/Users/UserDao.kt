package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserDao {
    fun getUserInfo(token: String): UserInfoDto

    fun getUser(token: String): Int?

}