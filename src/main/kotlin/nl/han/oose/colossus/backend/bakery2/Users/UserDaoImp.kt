package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class UserDaoImp : UserDao{
    @Autowired
    private lateinit var userMapper : UserMapper
    @Autowired
    private lateinit var dbConnection: DatabaseConnection
    override fun getUserInfo(token: String): UserInfoDto {
        val preparedStatement = dbConnection.prepareStatement("select u.firstname, u.lastname, t.teamname, tr.roomno from user u inner join userinteam ut on u.code = ut.code inner join teaminroom tr on ut.teamid = tr.teamid inner join team t on t.TEAMID = ut.TEAMID where u.code = (select code from usersession where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserInfo(resultSet)
        resultSet.close()
        preparedStatement.close()
        return user

    }


        override fun getUser(token: String): Int {
            var user : Int = 0
            val preparedStatement = dbConnection.prepareStatement("select code from user where code = (select code from usersession where token = ?)")
            preparedStatement.setString(1, token)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                user = resultSet.getInt("code")
            }
            resultSet.close()
            preparedStatement.close()
            return user
        }
}