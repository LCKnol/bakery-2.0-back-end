package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import java.sql.ResultSet

interface UserMapper {
    fun mapUserInfo(dataset : ResultSet) : UserInfoDTO
}