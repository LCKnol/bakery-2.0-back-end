package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class TeamServiceImp: TeamService {
    @Autowired
    private lateinit var teamDao: TeamDao

    override fun setTeamDao(teamDao: TeamDao) {
        this.teamDao = teamDao
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
}