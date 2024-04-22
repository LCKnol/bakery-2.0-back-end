package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class UserDaoImp : UserDao {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection
    override fun getUserInfo(token: String): UserInfoDto {
        val preparedStatement =
            dbConnection.prepareStatement("select u.firstname, u.lastname, t.teamname, tr.roomno from USERS u inner join USERINTEAM ut on u.userid = ut.userid inner join TEAMINROOM tr on ut.teamid = tr.teamid inner join TEAM t on t.TEAMID = ut.TEAMID where u.userid = (select userid from USERSESSION where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserInfo(resultSet)
        resultSet.close()
        preparedStatement.close()
        return user
    }

    override fun getUser(token: String): UserDto? {
        val preparedStatement =
            dbConnection.prepareStatement("select userid, firstname, lastname, password, email, isadmin from USERS where userid = (select userid from USERSESSION where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserId(resultSet)
        preparedStatement.close()
        return user
    }
}