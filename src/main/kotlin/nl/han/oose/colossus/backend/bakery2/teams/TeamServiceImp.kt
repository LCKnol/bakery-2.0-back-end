package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoDto
import nl.han.oose.colossus.backend.bakery2.rooms.RoomDao
import nl.han.oose.colossus.backend.bakery2.users.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class TeamServiceImp: TeamService {
    @Autowired
    private lateinit var teamDao: TeamDao

    @Autowired
    private lateinit var roomDao: RoomDao

    override fun setTeamDao(teamDao: TeamDao) {
        this.teamDao = teamDao
    }

    override fun setRoomDao(roomDao: RoomDao) {
        this.roomDao = roomDao
    }

    override fun getTeamsFromUser(userId: Int): TeamCollectionDto {
        return teamDao.getTeamsFromUser(userId)
    }

    override fun assignUserToTeam(userId: Int, teamId: Int) {
        teamDao.assignUserToTeam(userId,teamId)
    }

    override fun removeUserFromTeam(userId: Int, teamId: Int) {
        teamDao.removeUserFromTeam(userId,teamId)
    }

    override fun getAllTeams(): TeamCollectionDto {
        return teamDao.getAllTeams()
    }

    override fun getTeamsNotInRoom(roomNo: String): TeamCollectionDto {
        return teamDao.getTeamsNotInRoom(roomNo)
    }

    override fun addTeam(teamInfoDto: TeamInfoDto) {
        teamDao.addTeam(teamInfoDto)
        val team = teamDao.getTeam(teamInfoDto.getName())
        if (teamInfoDto.getMembers().size != 0) {
            for (user in teamInfoDto.getMembers()) {
                teamDao.assignUserToTeam(user.getId(), team.getId())
            }
        }
        if (teamInfoDto.getRooms().size != 0) {
            for (room in teamInfoDto.getRooms()) {
                roomDao.addTeamToRoom(room.getRoomNo(), team.getId())
            }
        }
    }

    override fun removeTeam(teamId: Int) {
        teamDao.removeTeam(teamId)
    }

    override fun getAllTeamInfo(): TeamInfoCollectionDto {
        return teamDao.getAllTeamInfo()
    }

}