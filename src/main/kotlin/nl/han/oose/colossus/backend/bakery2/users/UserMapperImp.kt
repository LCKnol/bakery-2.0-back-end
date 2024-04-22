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
                teamDTO.setName(teamname)
                teams.add(teamDTO)
            }
            if (uniqueRooms.add(roomno)) {
                val roomDTO = RoomDto()
                roomDTO.setRoomNo(roomno)
                rooms.add(roomDTO)
            }
        }

        userInfoDTO.setFirstname(firstname ?: "")
        userInfoDTO.setLastname(lastname ?: "")
        userInfoDTO.setTeams(teams)
        userInfoDTO.setRooms(rooms)
        return userInfoDTO
    }

    override fun mapUserId(resultSet: ResultSet): UserDto? {
        var user: UserDto? = null
        while (resultSet.next()) {
            user = UserDto(
                resultSet.getInt("userid"),
                resultSet.getString("FIRSTNAME"),
                resultSet.getString("LASTNAME"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("EMAIL"),
                resultSet.getBoolean("ISADMIN")
            )
        }
        return user
    }
}

