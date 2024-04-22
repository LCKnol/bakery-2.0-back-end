package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import java.sql.ResultSet

interface UserMapper {
    fun mapUserInfo(resultSet : ResultSet) : UserInfoDto
    fun mapUserId(resultSet : ResultSet) : UserDto?
}