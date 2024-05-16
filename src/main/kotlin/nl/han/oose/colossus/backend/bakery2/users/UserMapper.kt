package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import java.sql.ResultSet

interface UserMapper {
    fun mapUserInfo(resultSet : ResultSet) : UserInfoDto
    fun mapUser(resultSet : ResultSet) : UserDto?
}