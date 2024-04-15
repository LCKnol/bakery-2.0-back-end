package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.RoomDTO
import nl.han.oose.colossus.backend.bakery2.dto.TeamDTO
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import org.springframework.context.annotation.Primary

@Primary
class UserDaoImp : UserDao{
    override fun getUserInfo(token: String): UserInfoDTO {
        var room = RoomDTO()
        var team = TeamDTO()
        var team2 = TeamDTO()
        val user = UserInfoDTO()
        room.setRoomNo("12-12")
        team.setName("team1")
        user.setName("Pieter post")
        user.setTeams(arrayListOf(team, team2))
        user.setRooms(arrayListOf(room))

        return user
    }


        override fun getUser(token: String): Int {
            var user = 1
            return user
        }
}