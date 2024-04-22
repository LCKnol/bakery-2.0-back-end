package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserService {

    fun getUserInfo(token: String): UserInfoDto

    fun setUserDao(dao: UserDao)

    fun getUserId(token: String): Int
}