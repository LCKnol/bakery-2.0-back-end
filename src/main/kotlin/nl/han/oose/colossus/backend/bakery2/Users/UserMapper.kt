package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import java.sql.ResultSet

interface UserMapper {
    fun mapUserInfo(dataset : ResultSet) : UserInfoDto
}