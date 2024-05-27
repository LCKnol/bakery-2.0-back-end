package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoDto

interface TeamService {
    fun setTeamDao(teamDao: TeamDao)
    fun getTeamsFromUser(userId: Int): TeamCollectionDto
    fun assignUserToTeam(userId: Int, teamId: Int)
    fun removeUserFromTeam(userId: Int, teamId: Int)
    fun getAllTeams(): TeamCollectionDto

    fun getTeamsNotInRoom(roomNo: String): TeamCollectionDto

     fun addTeam(teamInfoDto: TeamInfoDto)

}