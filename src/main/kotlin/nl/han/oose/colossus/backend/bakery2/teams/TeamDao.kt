package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoDto

interface TeamDao {
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setTeamMapper(mapper: TeamMapper)
    fun getTeams(userId: Int): TeamCollectionDto
    fun getTeamsNotInRoom(roomNo: String): TeamCollectionDto
    fun getTeamsFromUser(userId: Int): TeamCollectionDto
    fun assignUserToTeam(userId: Int, teamId: Int)
    fun removeUserFromTeam(userId: Int, teamId: Int)
    fun getAllTeams(): TeamCollectionDto
    fun addTeam(teamInfoDto: TeamInfoDto)
    fun getTeam(teamName: String): TeamDto
    fun removeTeam(teamId: Int)
    fun getAllTeamInfo(): TeamInfoCollectionDto
}