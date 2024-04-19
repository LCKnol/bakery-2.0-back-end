package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.Pi.PiMapper
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

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setUserMapper(mapper: UserMapper) {
        userMapper = mapper
    }
    override fun getUserInfo(token: String): UserInfoDto {
        val preparedStatement = dbConnection.prepareStatement("select u.firstname, u.lastname, t.teamname, tr.roomno from users u inner join userinteam ut on u.userid = ut.userid inner join teaminroom tr on ut.teamid = tr.teamid inner join team t on t.TEAMID = ut.TEAMID where u.userid = (select userid from usersession where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserInfo(resultSet)
        resultSet.close()
        preparedStatement.close()
        return user

    }


        override fun getUser(token: String): Int {
            var user : Int = 0
            val preparedStatement = dbConnection.prepareStatement("select userid from users where userid = (select userid from usersession where token = ?)")
            preparedStatement.setString(1, token)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                user = resultSet.getInt("userid")
            }
            resultSet.close()
            preparedStatement.close()
            return user
        }
}