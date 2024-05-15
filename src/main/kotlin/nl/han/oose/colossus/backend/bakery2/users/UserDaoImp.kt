package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.SQLException

@Primary
@Component
class UserDaoImp : UserDao {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setUserMapper(mapper: UserMapper) {
        userMapper = mapper
    }

    override fun getUserInfo(token: String): UserInfoDto {
        val preparedStatement =
            dbConnection.prepareStatement("select u.firstname, u.lastname, t.teamname, tr.roomno from USERS u left join USERINTEAM ut on u.userid = ut.userid left join TEAMINROOM tr on ut.teamid = tr.teamid left join TEAM t on t.TEAMID = ut.TEAMID where u.userid = (select userid from USERSESSION where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserInfo(resultSet)
        resultSet.close()
        preparedStatement.close()
        return user
    }

    override fun getUser(token: String): UserDto? {
        val preparedStatement =
            dbConnection.prepareStatement("SELECT userid, firstname, lastname, password, email, isadmin FROM USERS WHERE userid = (SELECT userid FROM USERSESSION WHERE token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUser(resultSet)
        preparedStatement.close()
        return user
    }

    override fun insertUser(userDto: UserDto) {
        val query = "INSERT INTO USERS(firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)"
        try {
            val preparedStatement = dbConnection.prepareStatement(query)
            preparedStatement.setString(1, userDto.getFirstName())
            preparedStatement.setString(2, userDto.getLastName())
            preparedStatement.setString(3, userDto.getEmail())
            preparedStatement.setString(4, userDto.getPassword())
            preparedStatement.setBoolean(5, userDto.getIsAdmin())
            preparedStatement.executeUpdate()
            preparedStatement.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}