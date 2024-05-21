package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpForbiddenException
import nl.han.oose.colossus.backend.bakery2.teams.TeamDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Primary
@Component
class UserServiceImp : UserService {
    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var teamDao: TeamDao

    override fun setUserDao(dao: UserDao) {
        userDao = dao
    }

    override fun setTeamDao(dao: TeamDao) {
        teamDao = dao
    }

    override fun getUserInfo(token: String): UserInfoDto {
        val user = userDao.getUserInfo(token)
        return user
    }



    override fun getUserId(token: String): Int {
        val user: UserDto? = userDao.getUser(token)
        if (user != null) {
            val id = user.getId()
            return id
        }
        // TODO: decide how to handle this: throw some exception???
        return -1
    }

    override fun registerUser(userDto: UserDto) {
        userDto.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()))
        userDao.insertUser(userDto)
    }

    override fun checkUserInTeam(userId: Int, teamId: Int) {
        val teamsOfUser = teamDao.getTeams(userId)
        for (team in teamsOfUser.getTeamCollection()) {
            if (team.getId() == teamId) {
                return
            }
        }
        throw HttpForbiddenException("User is not in given team")
    }

    override fun getAllUsers(): UserCollectionDto {
        return userDao.getAllUsers()
    }

    override fun deleteUser(userId: Int) {
        userDao.deleteUser(userId)
    }
}





