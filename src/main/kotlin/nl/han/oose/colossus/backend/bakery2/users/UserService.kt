package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.teams.TeamDao

interface UserService {

    fun getUserInfo(token: String): UserInfoDto

    fun setUserDao(dao: UserDao)

    fun setTeamDao(dao: TeamDao)

    fun getUserId(token: String): Int

    fun registerUser(userDto: UserDto)

    fun checkUserInTeam(userId: Int, teamId: Int)
}