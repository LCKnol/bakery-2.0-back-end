package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO

interface UserDao {
    fun getUserInfo(token: String): UserInfoDTO

    fun getUser(token: String): Int

}