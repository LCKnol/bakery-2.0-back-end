package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Primary
@Component
class UserMapperImp : UserMapper {
    override fun mapUserInfo(resultSet: ResultSet): UserInfoDTO {
        var userInfoDTO = UserInfoDTO()
        var firstname: String? = null
        var lastname: String? = null
        val uniqueTeams = HashSet<String>()
        val uniqueRooms = HashSet<String>()
        val teams = ArrayList<TeamDTO>()
        val rooms = ArrayList<RoomDTO>()

        while (resultSet.next()) {
            firstname = resultSet.getString("firstname")
            lastname = resultSet.getString("lastname")

            val teamname = resultSet.getString("teamname")
            val roomno = resultSet.getString("roomno")

            if (uniqueTeams.add(teamname)) {
                val teamDTO = TeamDTO()
                teamDTO.setName(teamname)
                teams.add(teamDTO)
            }
            if (uniqueRooms.add(roomno)) {
                val roomDTO = RoomDTO()
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
}

