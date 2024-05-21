package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Primary
@Component
class UserMapperImp : UserMapper {
    override fun mapUserInfo(resultSet: ResultSet): UserInfoDto {
        val userInfoDTO = UserInfoDto()
        var firstname: String? = null
        var lastname: String? = null
        val uniqueTeams = HashSet<String>()
        val uniqueRooms = HashSet<String>()
        val teams = ArrayList<TeamDto>()
        val rooms = ArrayList<RoomDto>()

        while (resultSet.next()) {
            firstname = resultSet.getString("firstname")
            lastname = resultSet.getString("lastname")

            val teamname = resultSet.getString("teamname")
            val roomno = resultSet.getString("roomno")

            if (uniqueTeams.add(teamname)) {
                val teamDTO = TeamDto()
                teamDTO.setName(teamname ?: "")
                teams.add(teamDTO)
            }
            if (uniqueRooms.add(roomno)) {
                val roomDTO = RoomDto()
                roomDTO.setRoomNo(roomno ?: "")
                rooms.add(roomDTO)
            }
        }

        userInfoDTO.setFirstname(firstname ?: "")
        userInfoDTO.setLastname(lastname ?: "")
        userInfoDTO.setTeams(teams)
        userInfoDTO.setRooms(rooms)
        return userInfoDTO
    }

    override fun mapUser(resultSet: ResultSet): UserDto? {
        var user: UserDto? = null
        while (resultSet.next()) {
            val teams = ArrayList<TeamDto>()
            user = UserDto(
                resultSet.getInt("userid"),
                resultSet.getString("FIRSTNAME"),
                resultSet.getString("LASTNAME"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("EMAIL"),
                teams,
                resultSet.getBoolean("ISADMIN")
            )

        }
        return user
    }

    override fun mapUserCollection(resultSet: ResultSet): UserCollectionDto {
        var users: UserCollectionDto = UserCollectionDto()
        var userlist = ArrayList<UserDto>()
        while (resultSet.next()) {
            var currentId = resultSet.getInt("userid")
            if (!userlist.any{item -> item.getId() == currentId}){
                var teams = ArrayList<TeamDto>()
                var  user = UserDto(
                    resultSet.getInt("userid"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("EMAIL"),
                    resultSet.getString("PASSWORD"),
                    teams,
                    resultSet.getBoolean("ISADMIN")
                )
                userlist.add(user)
            }
            var user = userlist.filter { item -> item.getId() == currentId }[0]
            var teams = user.getTeams()
            var team = TeamDto()
            team.setName(resultSet.getString("teamname"))
            team.setId(resultSet.getInt("teamid"))
            teams.add(team)

        }
        users.setUserCollection(userlist)
        return users
    }

}

