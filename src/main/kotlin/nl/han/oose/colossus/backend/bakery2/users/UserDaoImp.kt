package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import java.sql.SQLException

@Primary
@Component
class UserDaoImp : UserDao {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        this.databaseConnection = connection
    }

    override fun setUserMapper(mapper: UserMapper) {
        userMapper = mapper
    }

    @Throws(ServerErrorException::class)
    override fun getUserInfo(token: String): UserInfoDto {
        val connection = databaseConnection.getConnection()
        val preparedStatement = connection.prepareStatement("select u.firstname, u.lastname, t.teamname, tr.roomno from USERS u left join USERINTEAM ut on u.userid = ut.userid left join TEAMINROOM tr on ut.teamid = tr.teamid left join TEAM t on t.TEAMID = ut.TEAMID where u.userid = (select userid from USERSESSION where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUserInfo(resultSet)
        resultSet.close()
        preparedStatement.close()
        connection.close()
        return user
    }

    @Throws(ServerErrorException::class)
    override fun getUser(token: String): UserDto? {
        val connection = databaseConnection.getConnection()
        val preparedStatement = connection.prepareStatement("SELECT u.userid, u.firstname, u.lastname, u.password, u.email, u.isadmin, t.teamname from USERS u left join USERINTEAM ut on u.userid = ut.userid left join TEAMINROOM tr on ut.teamid = tr.teamid left join TEAM t on t.TEAMID = ut.TEAMID where u.userid = (select userid from USERSESSION where token = ?)")
        preparedStatement.setString(1, token)
        val resultSet = preparedStatement.executeQuery()
        val user = userMapper.mapUser(resultSet)
        preparedStatement.close()
        connection.close()
        return user
    }

    @Throws(ServerErrorException::class)
    override fun insertUser(userDto: UserDto) {
        val connection = databaseConnection.getConnection()
        val query = "INSERT INTO USERS(firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)"
        try {
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, userDto.getFirstName())
            preparedStatement.setString(2, userDto.getLastName())
            preparedStatement.setString(3, userDto.getEmail())
            preparedStatement.setString(4, userDto.getPassword())
            preparedStatement.setBoolean(5, userDto.getIsAdmin())
            preparedStatement.executeUpdate()
            preparedStatement.close()
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    override fun getAllUsers(): UserCollectionDto {
        val connection = databaseConnection.getConnection()
        val preparedStatement = connection.prepareStatement("SELECT distinct   u.userid, u.firstname, u.lastname, u.password, u.email, u.isadmin, t.teamname from USERS u left join USERINTEAM ut on u.userid = ut.userid left join TEAMINROOM tr on ut.teamid = tr.teamid left join TEAM t on t.TEAMID = ut.TEAMID ")
        val resultSet = preparedStatement.executeQuery()
        val users = userMapper.mapUserCollection(resultSet)
        preparedStatement.close()
        connection.close()
        return users!!
    }
}