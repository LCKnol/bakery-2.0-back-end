package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto

interface UserService {

    fun getUserInfo(token: String): UserInfoDto
    fun getUser(token: String): Int

    fun setUserDao(dao: UserDao)

}