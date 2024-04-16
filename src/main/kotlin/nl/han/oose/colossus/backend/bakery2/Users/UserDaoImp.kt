package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.RoomDTO
import nl.han.oose.colossus.backend.bakery2.dto.TeamDTO
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import org.springframework.context.annotation.Primary

@Primary
class UserDaoImp : UserDao{
    val dbConnection = DatabaseConnection()
    val userMapper = UserMapperImp()
    override fun getUserInfo(token: String): UserInfoDTO {
//        val preparedStatement = dbConnection.prepareStatement("SELECT u.firstname, u.lastname, ut.teamid, tr.roomno FROM user u INNER JOIN userinteam ut ON u.code = ut.code INNER JOIN teaminroom tr ON ut.teamid = tr.teamid WHERE u.code = (SELECT code FROM usersession WHERE token = '?')")
//        preparedStatement.setString(1, token)
//        val resultSet = preparedStatement.executeQuery()
//        val user = userMapper.mapUserInfo(resultSet)
//        resultSet.close()
//        preparedStatement.close()
//        return user



        var room = RoomDTO()
        var team = TeamDTO()
        var team2 = TeamDTO()
        val user = UserInfoDTO()
        room.setRoomNo("12-13")
        team.setName("team1")
        user.setName("Pieter post")
        user.setTeams(arrayListOf(team, team2))
        user.setRooms(arrayListOf(room))

        return user
    }


        override fun getUser(token: String): Int {
            var user : Int = 0
            val preparedStatement = dbConnection.prepareStatement("select code from user where code = (select code from usersession where token = ?)")
            preparedStatement.setString(1, token)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                // Retrieve data from result set
                user = resultSet.getInt("code")
            }
            resultSet.close()
            preparedStatement.close()
            return user
        }
}